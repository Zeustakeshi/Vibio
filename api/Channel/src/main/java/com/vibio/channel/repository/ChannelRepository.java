/*
 *  ChannelRepository
 *  @author: Minhhieuano
 *  @created 9/27/2024 11:14 PM
 * */

package com.vibio.channel.repository;

import com.vibio.channel.model.Channel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChannelRepository extends JpaRepository<Channel, String> {
    Optional<Channel> findByAccountId(String accountId);

    boolean existsByName(String name);
}