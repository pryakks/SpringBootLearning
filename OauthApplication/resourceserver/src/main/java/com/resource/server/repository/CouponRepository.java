package com.resource.server.repository;

import com.resource.server.model.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CouponRepository extends JpaRepository<Coupon, Long> {

	Coupon findByCode(String code);

}
