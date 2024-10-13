/*
 *  Comment
 *  @author: Minhhieuano
 *  @created 10/6/2024 8:37 AM
 * */

package com.vibio.video.entity;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
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
public class Comment {

    @Id
    @Builder.Default
    private String id = NanoIdUtils.randomNanoId();

    @ManyToOne
    private Video video;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @ManyToOne()
    private Comment parent;

    @OneToMany(mappedBy = "parent", orphanRemoval = true, cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Comment> replies;

    private boolean isReply;

    @Builder.Default
    private Integer replyCount = 0;

    @Builder.Default
    private boolean updated = false;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
