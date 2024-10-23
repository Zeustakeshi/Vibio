/*
 *  PlaylistVideoRepository
 *  @author: Minhhieuano
 *  @created 10/17/2024 5:13 PM
 * */

package com.vibio.channel.repository;

import com.vibio.channel.model.PlaylistVideo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlaylistVideoRepository extends JpaRepository<PlaylistVideo, String> {
    Integer countByPlaylistId(String playlistId);

    boolean existsByPlaylistIdAndVideoId(String playlistId, String videoId);

    Optional<PlaylistVideo> findByPlaylistIdAndVideoId(String playlistId, String videoId);

    @Query("select v from PlaylistVideo v where v.playlist.id = :playlistId and v.playlist.visibility != 'PRIVATE'")
    Page<PlaylistVideo> findAllByPublicPlaylistId(String playlistId, Pageable pageable);

    @Query("select v from PlaylistVideo v where v.playlist.id = :playlistId and v.playlist.channel.accountId = :accountId")
    Page<PlaylistVideo> findByPlaylistIdAndAccountId(String playlistId, String accountId, Pageable pageable);

    @Modifying
    @Query("update PlaylistVideo  v set v.order = v.order + 1 where v.playlist.id = :playlistId and v.order >= :start and v.order <= :end")
    void increasePlaylistVideoOrderInRange(String playlistId, int start, int end);

    @Modifying
    @Query("update PlaylistVideo  v set v.order = v.order - 1 where v.playlist.id = :playlistId and v.order >= :start and v.order <= :end")
    void decreasePlaylistVideoOrderInRange(String playlistId, int start, int end);
}
