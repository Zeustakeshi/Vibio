/*
 *  ChannelRepository
 *  @author: Minhhieuano
 *  @created 9/27/2024 11:14 PM
 * */

package com.vibio.channel.repository;

import com.vibio.channel.model.Channel;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChannelRepository extends JpaRepository<Channel, String> {
    Optional<Channel> findByAccountId(String accountId);

    boolean existsById(@NonNull String id);

    boolean existsByName(String name);

    List<Channel> findAllByIdIn(List<String> channelIds);
}
