/*
 *  Video
 *  @author: Minhhieuano
 *  @created 9/29/2024 4:05 PM
 * */

package com.vibio.video.entity;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import com.vibio.video.common.enums.UploadStatus;
import com.vibio.video.common.enums.Visibility;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Video {

	@Id
	@Builder.Default
	private String id = NanoIdUtils.randomNanoId();

	@Column(nullable = false)
	private String channelId;

	@Column(nullable = false)
	private String title;

	@Column(nullable = false, columnDefinition = "TEXT")
	private String description;

	private String url;

	private String thumbnail;

	@Builder.Default
	private Integer viewCount = 0;

	@Builder.Default
	private Integer likeCount = 0;

	@Builder.Default
	private Integer dislikeCount = 0;

	@Builder.Default
	private Integer commentCount = 0;

	@ElementCollection
	private Set<String> tags;

	@Builder.Default
	@Enumerated(EnumType.STRING)
	private UploadStatus uploadStatus = UploadStatus.PENDING;

	@Builder.Default
	@Enumerated(EnumType.STRING)
	private Visibility visibility = Visibility.PRIVATE;

	@Builder.Default
	private boolean allowedComment = false;

	@CreationTimestamp
	private LocalDateTime createdAt;

	@UpdateTimestamp
	private LocalDateTime updatedAt;
}
