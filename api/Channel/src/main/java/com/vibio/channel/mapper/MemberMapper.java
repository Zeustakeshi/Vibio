/*
 *  MemberMapper
 *  @author: Minhhieuano
 *  @created 10/25/2024 10:31 PM
 * */


package com.vibio.channel.mapper;

import com.vibio.channel.dto.response.MemberResponse;
import com.vibio.channel.model.Member;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MemberMapper {
    MemberResponse memberToMemberResponse(Member member);
}
