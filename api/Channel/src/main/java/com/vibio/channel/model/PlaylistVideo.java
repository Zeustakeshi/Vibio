/*
 *  VideoPlaylist
 *  @author: Minhhieuano
 *  @created 10/17/2024 5:08 PM
 * */


package com.vibio.channel.model;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class VideoPlaylist {
    @Id
    @Builder.Default
    private String id = NanoIdUtils.randomNanoId();

    @ManyToOne
    @JoinColumn(nullable = false)
    private Playlist playlist;

    private Integer order;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
