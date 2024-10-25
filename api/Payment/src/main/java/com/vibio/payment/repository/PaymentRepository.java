/*
 *  PaymentRepository
 *  @author: Minhhieuano
 *  @created 10/24/2024 3:19 PM
 * */

package com.vibio.payment.repository;

import com.vibio.payment.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, String> {
}
