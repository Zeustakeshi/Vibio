/*
 *  Subscription
 *  @author: Minhhieuano
 *  @created 10/10/2024 8:10 AM
 * */

package com.vibio.channel.model;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Subscription {
	@Id
	@Builder.Default
	private String id = NanoIdUtils.randomNanoId();

	@Column(nullable = false)
	private String userId;

	@ManyToOne
	private Channel channel;

	@CreationTimestamp
	private LocalDateTime createdAt;

	@UpdateTimestamp
	private LocalDateTime updatedAt;
}
