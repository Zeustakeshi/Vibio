/*
 *  VideoReactionRepository
 *  @author: Minhhieuano
 *  @created 10/13/2024 10:29 AM
 * */

package com.vibio.video.repository;

import com.vibio.video.common.enums.ReactionType;
import com.vibio.video.entity.VideoReaction;
import com.vibio.video.entity.key.VideoReactionKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VideoReactionRepository extends JpaRepository<VideoReaction, VideoReactionKey> {

    @Query(
            "select count(reaction) > 0 from VideoReaction reaction where reaction.id.videoId = :videoId and reaction.id.userId = :userId and reaction.type = :reactionType")
    boolean existsByVideoIdAndUserIdAndReactionType(String videoId, String userId, ReactionType reactionType);

    @Query(
            "select reaction from VideoReaction reaction where reaction.id.videoId = :videoId and reaction.id.userId = :userId and reaction.type = :reactionType")
    Optional<VideoReaction> findByVideoIdAndUserIdAndReactionType(
            String videoId, String userId, ReactionType reactionType);

    @Query("select count(*) from VideoReaction reaction where  reaction.id.videoId = :videoId and reaction.type = 'LIKE'")
    Integer countLikeByVideoId(String videoId);

    @Query("select count(*) from VideoReaction reaction where  reaction.id.videoId = :videoId and reaction.type = 'DISLIKE'")
    Integer countDislikeByVideoId(String videoId);

}
