/*
 *  PlaylistRepository
 *  @author: Minhhieuano
 *  @created 10/17/2024 5:12 PM
 * */

package com.vibio.channel.repository;

import com.vibio.channel.model.Playlist;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaylistRepository extends JpaRepository<Playlist, String> {

	@Query("select count(p) > 0 from Playlist p where p.name = :name and p.channel.id = :channelId")
	boolean existsByNameAndChannelId(String name, String channelId);

	boolean existsByIdAndChannelId(String id, String channelId);

	Page<Playlist> findAllByChannelId(String channelId, Pageable pageable);

	@Query("select p.name from Playlist  p where p.channel.id = :channelId")
	Page<String> findAllNameByChannelId(String channelId, Pageable pageable);

	@Query("select p from Playlist p where p.channel.id = :channelId and p.visibility != 'PRIVATE'")
	Page<Playlist> findAllPublicByChannelId(String channelId, Pageable pageable);

	Optional<Playlist> findByIdAndChannelId(String id, String channelId);

	@Query("select p from Playlist p where p.id = :playlistId and p.visibility != 'PRIVATE'")
	Optional<Playlist> findPublicById(String playlistId);
}
