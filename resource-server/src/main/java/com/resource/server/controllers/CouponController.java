package com.resource.server.controllers;

import com.resource.server.model.Coupon;
import com.resource.server.repository.CouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/couponapi")
public class CouponController {

	@Autowired
	CouponRepository couponRepository;

	@PostMapping("/coupons")
	public Coupon create(@RequestBody Coupon coupon) {
		return couponRepository.save(coupon);

	}

	@GetMapping("/coupons/{code}")
	public Coupon getCoupon(@PathVariable("code") String code) {
		return couponRepository.findByCode(code);

	}
}
