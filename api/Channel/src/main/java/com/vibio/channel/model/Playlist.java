/*
 *  Playlist
 *  @author: Minhhieuano
 *  @created 10/17/2024 5:05 PM
 * */


package com.vibio.user.model;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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
public class Playlist {
    @Id
    @Builder.Default
    private String id = NanoIdUtils.randomNanoId();

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @ManyToOne
    private Account account;

    private Integer videoCount;

    @Column(columnDefinition = "TEXT")
    private String description;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
