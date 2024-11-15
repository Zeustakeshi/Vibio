/*
 *  VideoRepository
 *  @author: Minhhieuano
 *  @created 9/29/2024 4:20 PM
 * */

package com.vibio.video.repository;

import com.vibio.video.entity.Video;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VideoRepository extends JpaRepository<Video, String> {
    Page<Video> findAllByChannelId(String channelId, Pageable pageable);

    @Query("select v from Video v where v.visibility = 'PUBLIC'")
    Page<Video> findAllPublicVideo(Pageable pageable);

    @Query("select v from Video v where v.channelId = :channelId and v.visibility != 'PRIVATE'")
    Page<Video> findAllPublicByChannelId(String channelId, Pageable pageable);

    Optional<Video> findByIdAndChannelId(String videoId, String channelId);

    List<Video> findAllByIdIn(List<String> ids);

    boolean existsByIdAndChannelId(String videoId, String channelId);

    @Query("select count(v) > 0 from Video v where v.id = :videoId and v.ownerId = :accountId")
    boolean isVideoOwner(String videoId, String accountId);

    @Query("select count(v) > 0 from Video  v where v.id = :videoId and v.visibility = 'PUBLIC'")
    boolean existsAndPublicById(String videoId);


    @Query("select v.channelId from Video v where v.id = :videoId")
    Optional<String> getChannelIdByVideoId(String videoId);
}
