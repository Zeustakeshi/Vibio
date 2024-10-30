/*
 *  MemberInfoResponse
 *  @author: Minhhieuano
 *  @created 10/25/2024 10:26 PM
 * */


package com.vibio.channel.dto.response;

import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@Builder
public class MemberResponse {
    private String id;
    private LocalDateTime expireDate;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
