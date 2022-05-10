package com.coupan.service.app.oauth.service.controller;

import com.coupan.service.app.model.Coupon;
import com.coupan.service.app.oauth.service.Repository.UserRepository;
import com.coupan.service.app.oauth.service.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/register")
public class RegisterUserController {

    @Autowired
    UserRepository userRepository;

    @PostMapping("/user")
    public User create(@RequestBody User user){
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        return userRepository.save(user);
    }
}
