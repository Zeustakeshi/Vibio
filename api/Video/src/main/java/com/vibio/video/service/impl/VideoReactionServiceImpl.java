/*
 *  VideoReactionServiceImpl
 *  @author: Minhhieuano
 *  @created 10/13/2024 10:33 AM
 * */

package com.vibio.video.service.impl;

import com.vibio.video.common.enums.ReactionType;
import com.vibio.video.entity.Video;
import com.vibio.video.entity.VideoReaction;
import com.vibio.video.entity.key.VideoReactionKey;
import com.vibio.video.event.eventModel.ReactionVideoEvent;
import com.vibio.video.event.producer.VideoEventProducer;
import com.vibio.video.exception.ConflictException;
import com.vibio.video.exception.NotfoundException;
import com.vibio.video.repository.VideoReactionRepository;
import com.vibio.video.repository.VideoRepository;
import com.vibio.video.service.VideoReactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VideoReactionServiceImpl implements VideoReactionService {
	private final VideoReactionRepository videoReactionRepository;
	private final VideoRepository videoRepository;
	private final VideoEventProducer videoEventProducer;

	@Override
	public void reactionVideo(String videoId, String accountId, ReactionType reactionType) {

		Video video = videoRepository
				.findById(videoId)
				.orElseThrow(() -> new NotfoundException("Video " + videoId + " not found"));

		if (reactionType.equals(ReactionType.LIKE)) likeVideo(video, accountId);
		else dislikeVideo(video, accountId);
	}

	@Override
	public void unReactionVideo(String videoId, String accountId, ReactionType reactionType) {
		if (!videoRepository.existsById(videoId)) {
			throw new NotfoundException("Video " + videoId + " not found");
		}

		if (reactionType.equals(ReactionType.LIKE)) unLikeVideo(videoId, accountId);
		else unDislikeVideo(videoId, accountId);
	}

	private void likeVideo(Video video, String accountId) {
		if (videoReactionRepository.existsByVideoIdAndUserIdAndReactionType(
				video.getId(), accountId, ReactionType.LIKE)) {
			throw new ConflictException("You've liked with this video");
		}

		VideoReactionKey videoReactionKey = VideoReactionKey.builder()
				.videoId(video.getId())
				.userId(accountId)
				.build();

		VideoReaction videoReaction = VideoReaction.builder()
				.id(videoReactionKey)
				.video(video)
				.type(ReactionType.LIKE)
				.build();

		videoReactionRepository.save(videoReaction);

		ReactionVideoEvent event = ReactionVideoEvent.builder()
				.isReaction(true)
				.reactionType(ReactionType.LIKE)
				.videoId(video.getId())
				.userId(accountId)
				.build();

		videoEventProducer.reactionVideo(event);

		unDislikeVideo(video.getId(), accountId);
	}

	private void dislikeVideo(Video video, String accountId) {
		if (videoReactionRepository.existsByVideoIdAndUserIdAndReactionType(
				video.getId(), accountId, ReactionType.DISLIKE)) {
			throw new ConflictException("You've disliked with this video");
		}

		VideoReactionKey videoReactionKey = VideoReactionKey.builder()
				.videoId(video.getId())
				.userId(accountId)
				.build();

		VideoReaction videoReaction = VideoReaction.builder()
				.id(videoReactionKey)
				.video(video)
				.type(ReactionType.DISLIKE)
				.build();

		videoReactionRepository.save(videoReaction);

		ReactionVideoEvent event = ReactionVideoEvent.builder()
				.isReaction(true)
				.reactionType(ReactionType.DISLIKE)
				.videoId(video.getId())
				.userId(accountId)
				.build();

		videoEventProducer.reactionVideo(event);

		unLikeVideo(video.getId(), accountId);
	}

	private void unLikeVideo(String videoId, String accountId) {
		videoReactionRepository
				.findByVideoIdAndUserIdAndReactionType(videoId, accountId, ReactionType.LIKE)
				.ifPresent(videoReactionRepository::delete);

		ReactionVideoEvent event = ReactionVideoEvent.builder()
				.isReaction(false)
				.reactionType(ReactionType.LIKE)
				.videoId(videoId)
				.userId(accountId)
				.build();

		videoEventProducer.reactionVideo(event);
	}

	private void unDislikeVideo(String videoId, String accountId) {
		videoReactionRepository
				.findByVideoIdAndUserIdAndReactionType(videoId, accountId, ReactionType.DISLIKE)
				.ifPresent(videoReactionRepository::delete);

		ReactionVideoEvent event = ReactionVideoEvent.builder()
				.isReaction(false)
				.reactionType(ReactionType.DISLIKE)
				.videoId(videoId)
				.userId(accountId)
				.build();

		videoEventProducer.reactionVideo(event);
	}
}
