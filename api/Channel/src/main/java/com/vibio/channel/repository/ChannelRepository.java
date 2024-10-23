/*
 *  ChannelRepository
 *  @author: Minhhieuano
 *  @created 9/27/2024 11:14 PM
 * */

package com.vibio.channel.repository;

import com.vibio.channel.model.Channel;
import java.util.List;
import java.util.Optional;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ChannelRepository extends JpaRepository<Channel, String> {
	Optional<Channel> findByAccountId(String accountId);

	boolean existsById(@NonNull String id);

	boolean existsByName(String name);

	@Query("select count (c) > 0 from Channel c where c.id = :channelId and c.accountId = :accountId")
	boolean isChannelOwner(String accountId, String channelId);

	List<Channel> findAllByIdIn(List<String> channelIds);
}
