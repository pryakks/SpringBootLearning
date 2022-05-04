package com.payment.repository;

import com.payment.model.PaymentInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentInfoRepository  extends JpaRepository<PaymentInfo, String> {
}
