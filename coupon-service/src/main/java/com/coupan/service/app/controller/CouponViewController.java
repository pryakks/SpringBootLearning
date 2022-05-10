package com.coupan.service.app.controller;

import com.coupan.service.app.model.Coupon;
import com.coupan.service.app.repository.CouponRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CouponViewController {

    @Autowired
    CouponRepo couponRepo;

//    @GetMapping("/")
//    private String index(){
//        return "index";
//    }

    @GetMapping("/showCreateCoupon")
    private String showCreateCoupon(){
        return "createCoupon";
    }

    @PostMapping("/saveCoupon")
    private String saveCoupon(Coupon coupon){
        couponRepo.save(coupon);
        return "createResponse";
    }

    @GetMapping("/showGetCoupon")
    public String showGetCoupon(){
        return "getCoupon";
    }

    @PostMapping("/getCoupon")
    public ModelAndView getCoupon(String code){
       ModelAndView mav =  new ModelAndView("couponDetails");
       mav.addObject(couponRepo.findByCode(code));
       return mav;
    }
}
