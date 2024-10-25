/*
 *  MemberPayment
 *  @author: Minhhieuano
 *  @created 10/24/2024 3:01 PM
 * */


package com.vibio.payment.entity;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import com.vibio.payment.common.enums.PaymentMethod;
import com.vibio.payment.common.enums.PaymentStatus;
import com.vibio.payment.common.enums.TransactionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Payment {
    @Id
    @Builder.Default
    private String id = NanoIdUtils.randomNanoId();

    @Column(nullable = false, updatable = false)
    private String userId;

    @Column(nullable = false, updatable = false)
    private String referenceId;

    @Column(nullable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    @Column(nullable = false, updatable = false)
    private Long amount;

    @Column(nullable = false, updatable = false)
    private String payInfo;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private PaymentStatus status = PaymentStatus.PENDING;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, updatable = false)
    private PaymentMethod method;

    private String payType;

    private String transId;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
