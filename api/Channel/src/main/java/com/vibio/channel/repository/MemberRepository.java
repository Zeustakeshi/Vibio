/*
 *  MemberRepository
 *  @author: Minhhieuano
 *  @created 10/25/2024 12:55 AM
 * */

package com.vibio.channel.repository;

import com.vibio.channel.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, String> {
    @Query(
            "select count(m) > 0 from Member m where m.channelId = :channelId and m.accountId = :accountId and m.expireDate >= current_timestamp ")
    boolean existsByChannelIdAndAccountId(String channelId, String accountId);
}
