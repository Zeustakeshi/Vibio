/*
 *  VideoRepository
 *  @author: Minhhieuano
 *  @created 9/29/2024 4:20 PM
 * */

package com.vibio.video.repository;

import com.vibio.video.entity.Video;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoRepository extends JpaRepository<Video, String> {
	Page<Video> findAllByChannelId(String channelId, Pageable pageable);

	@Query("select v from Video v where v.visibility = 'PUBLIC'")
	Page<Video> findAllPublicVideo(Pageable pageable);

	Optional<Video> findByIdAndChannelId(String videoId, String channelId);

	boolean existsByIdAndChannelId(String videoId, String channelId);
}
