/*
 *  Channel
 *  @author: Minhhieuano
 *  @created 9/27/2024 10:25 PM
 * */

package com.vibio.channel.model;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Channel {

	@Id
	@Builder.Default
	private String id = NanoIdUtils.randomNanoId();

	@Column(unique = true)
	private String accountId;

	@Column(unique = true)
	private String name;

	private String thumbnail;

	private String defaultEmail;

	private String background;

	private String description;

	@CreationTimestamp
	private LocalDateTime createdAt;

	@UpdateTimestamp
	private LocalDateTime updatedAt;
}
