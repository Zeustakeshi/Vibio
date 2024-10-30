/*
 *  MemberRepository
 *  @author: Minhhieuano
 *  @created 10/25/2024 12:55 AM
 * */

package com.vibio.channel.repository;

import com.vibio.channel.model.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, String> {
    @Query(
            "select count(m) > 0 from Member m where m.channelId = :channelId and m.accountId = :accountId and m.expireDate >= current_timestamp ")
    boolean existsByChannelIdAndAccountId(String channelId, String accountId);

    @Query(
            "select m from Member m where m.channelId = :channelId and m.accountId = :accountId and m.expireDate >= current_timestamp ")
    Optional<Member> findByChannelIdAndAccountId(String channelId, String accountId);

    @Query("select m from Member m where  m.channelId = :channelId")
    Page<Member> getAllByChannelId(String channelId, Pageable pageable);

    @Query("select m.accountId from Member m where m.channelId = :channelId and m.accountId in :userIds and m.expireDate >= current_timestamp")
    List<String> getExistingMembers(String channelId, List<String> userIds);
}
