package com.coupan.service.app.repository;

import com.coupan.service.app.model.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CouponRepo extends JpaRepository<Coupon, Long> {

    public Coupon findByCode(String code);
}
