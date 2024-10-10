/*
 *  CommentRepository
 *  @author: Minhhieuano
 *  @created 10/6/2024 8:56 AM
 * */

package com.vibio.video.repository;

import com.vibio.video.entity.Comment;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, String> {
	Page<Comment> findAllByVideoId(String videoId, Pageable pageable);

	Page<Comment> findAllByParentId(String parentId, Pageable pageable);

	Long countByVideoId(String videoId);

	Optional<Comment> findByIdAndUserId(String commentId, String userId);

	@Query("select c from Comment c where  c.video.id = :videoId and c.isReply = false")
	Page<Comment> getAllComment(String videoId, Pageable pageable);

	@Query(
			"select c from Comment c where  c.video.id = :videoId and c.video.visibility = 'PUBLIC' and c.isReply = false ")
	Page<Comment> getAllGuestComment(String videoId, Pageable pageable);

	@Query("select c from Comment c where c.isReply and c.parent.id = :parentId and c.video.id = :videoId")
	Page<Comment> getAllReplies(String videoId, String parentId, Pageable pageable);

	@Query(
			"select c from Comment c where  c.isReply and c.parent.id = :parentId and c.video.id = :videoId and c.video.visibility = 'PUBLIC'")
	Page<Comment> getAllGuestReplies(String videoId, String parentId, Pageable pageable);

	void deleteByIdAndUserIdAndVideoId(String id, String userId, String videoId);
}
