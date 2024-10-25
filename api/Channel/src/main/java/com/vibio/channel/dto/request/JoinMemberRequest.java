/*
 *  JoinMemberRequest
 *  @author: Minhhieuano
 *  @created 10/25/2024 1:08 PM
 * */


package com.vibio.channel.dto.request;

import com.vibio.channel.common.enums.PaymentMethod;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class JoinMemberRequest {
    @NotNull
    private PaymentMethod paymentMethod;

    @NotEmpty
    private String redirectUrl;
}
