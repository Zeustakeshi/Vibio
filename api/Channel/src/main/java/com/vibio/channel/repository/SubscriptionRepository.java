/*
 *  SubscriptionRepository
 *  @author: Minhhieuano
 *  @created 10/10/2024 8:19 AM
 * */

package com.vibio.channel.repository;

import com.vibio.channel.model.Subscription;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, String> {
    boolean existsByChannelIdAndUserId(String channelId, String userId);

    Optional<Subscription> findByChannelIdAndUserId(String channelId, String userId);

    Page<Subscription> findAllByUserId(String userId, Pageable pageable);

    Integer countByChannelId(String channelId);
}
