/*
 *  VideoRepository
 *  @author: Minhhieuano
 *  @created 11/1/2024 10:15 PM
 * */


package com.vibio.search.repository;

import com.vibio.search.entity.Video;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoRepository extends ElasticsearchRepository<Video, String> {
}
