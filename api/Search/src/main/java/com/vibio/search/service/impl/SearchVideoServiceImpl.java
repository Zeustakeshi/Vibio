/*
 *  VideoService
 *  @author: Minhhieuano
 *  @created 11/1/2024 10:16 PM
 * */


package com.vibio.search.service.impl;

import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import co.elastic.clients.elasticsearch._types.query_dsl.TextQueryType;
import com.vibio.search.dto.response.PageableResponse;
import com.vibio.search.dto.response.SearchVideoResponse;
import com.vibio.search.entity.Video;
import com.vibio.search.event.eventModel.UpdateVideoMetadataEvent;
import com.vibio.search.lib.Language;
import com.vibio.search.mapper.PageMapper;
import com.vibio.search.mapper.VideoMapper;
import com.vibio.search.repository.VideoRepository;
import com.vibio.search.service.SearchVideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchVideoServiceImpl implements SearchVideoService {
    private final VideoRepository videoRepository;
    private final VideoMapper videoMapper;
    private final PageMapper pageMapper;
    private final ElasticsearchOperations elasticsearchOperations;

    @Override
    public void saveVideoMetadata(UpdateVideoMetadataEvent event) {
        Video video = videoMapper.updateVideoMetadataEventToVideo(event);
        video.setUpdatedAt(LocalDateTime.now());
        video.setKeyword(Language.removeVietnameseTones(event.getTitle()));
        videoRepository.save(video);
    }

    @Override
    public List<String> searchVideoAutoComplete(String query) {
        var multiMatchQuery = QueryBuilders.multiMatch()
                .query(query)
                .type(TextQueryType.MostFields)
                .fields("title", "title._3gram").build();

        var fuzzyQuery = QueryBuilders.fuzzy()
                .queryName(query)
                .value(query)
                .field("title")
                .fuzziness("2").build();


        var disMaxQuery = QueryBuilders.disMax()
                .queries((q) -> q.multiMatch(multiMatchQuery))
                .queries(q -> q.fuzzy(fuzzyQuery))
                .boost(1.2f)
                .tieBreaker(1.0).build();

        NativeQuery searchQuery = NativeQuery.builder()
                .withQuery(q -> q.disMax(disMaxQuery))
                .withExplain(false)
                .build();
        SearchHits<Video> videoSearchHits = elasticsearchOperations.search(searchQuery, Video.class);

        return videoSearchHits.stream().map(hit -> hit.getContent().getTitle()).toList();
    }

    @Override
    public PageableResponse<SearchVideoResponse> searchVideo(String query, int page, int limit) {

        var multiMatchQuery = QueryBuilders.multiMatch()
                .query(query)
                .type(TextQueryType.BestFields)
                .fields("keyword^3", "title^2", "tags^2")
                .fuzziness("AUTO")
                .build();

        var matchChannelNameQuery = QueryBuilders.match()
                .query(query)
                .field("channel.name")
                .fuzziness("AUTO")
                .build();

        var boolQuery = QueryBuilders.bool()
                .should(q -> q.multiMatch(multiMatchQuery))
                .should(q -> q.match(matchChannelNameQuery))
                .build();

        Pageable pageable = PageRequest.of(
                page,
                limit,
                Sort.by(Sort.Order.desc("_score"),
                        Sort.Order.desc("updatedAt")
                ));

        var searchQuery = NativeQuery.builder()
                .withQuery(q -> q.bool(boolQuery))
                .withPageable(pageable)
                .build();

        SearchHits<Video> videoSearchHits = elasticsearchOperations.search(searchQuery, Video.class);

        List<Video> videos = videoSearchHits.stream().map(SearchHit::getContent).toList();

        Page<SearchVideoResponse> videoResults = new PageImpl<>(
                videos,
                pageable,
                videoSearchHits.getTotalHits()
        ).map(videoMapper::videoToSearchVideoResponse);

        return pageMapper.toPageableResponse(videoResults);
    }
}
