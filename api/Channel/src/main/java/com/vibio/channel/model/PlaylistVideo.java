/*
 *  VideoPlaylist
 *  @author: Minhhieuano
 *  @created 10/17/2024 5:08 PM
 * */

package com.vibio.channel.model;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import jakarta.persistence.*;
import java.time.LocalDateTime;
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
public class PlaylistVideo {
	@Id
	@Builder.Default
	private String id = NanoIdUtils.randomNanoId();

	@ManyToOne
	@JoinColumn(nullable = false)
	private Playlist playlist;

	@Column(nullable = false)
	private String videoId;

	private Integer order;

	@CreationTimestamp
	private LocalDateTime createdAt;

	@UpdateTimestamp
	private LocalDateTime updatedAt;
}
