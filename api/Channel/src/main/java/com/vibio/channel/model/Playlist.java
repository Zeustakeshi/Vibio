/*
 *  Playlist
 *  @author: Minhhieuano
 *  @created 10/17/2024 5:05 PM
 * */

package com.vibio.channel.model;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import com.vibio.channel.common.enums.Visibility;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

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

    @JoinColumn(nullable = false)
    @ManyToOne
    private Channel channel;

    private Integer videoCount;

    private String defaultThumbnail;

    @Column(columnDefinition = "TEXT")
    private String description;

    @OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, mappedBy = "playlist")
    private List<PlaylistVideo> videos;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private Visibility visibility = Visibility.PRIVATE;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
