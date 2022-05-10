package com.coupan.service.app.controller;

import com.coupan.service.app.model.Coupon;
import com.coupan.service.app.repository.CouponRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/couponapi")
public class CouponController {

    @Autowired
    CouponRepo couponRepo;

    @PostMapping("/coupons")
    public Coupon create(@RequestBody Coupon coupon){
       return couponRepo.save(coupon);
    }

    @GetMapping("/coupons/{code}")
    public Coupon getCoupon(@PathVariable String code){
        return couponRepo.findByCode(code);
    }

    @GetMapping("/sample")
    public String getCoupon(){
        return "this is samle";
    }
}
