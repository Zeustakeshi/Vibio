/*
 *  Member
 *  @author: Minhhieuano
 *  @created 10/24/2024 2:04 AM
 * */

package com.vibio.channel.model;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Member {

    @Id
    @Builder.Default
    private String id = NanoIdUtils.randomNanoId();

    @Column(nullable = false, updatable = false)
    private String channelId;

    @Column(nullable = false, updatable = false)
    private String accountId;

    @Column(nullable = false, updatable = false)
    private String paymentId;

    @Column(nullable = false)
    private LocalDateTime expireDate;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
