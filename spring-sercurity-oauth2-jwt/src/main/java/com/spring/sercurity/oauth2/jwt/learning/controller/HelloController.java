package com.spring.sercurity.oauth2.jwt.learning.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String getMessage(){
        return "Spring security Rocks!!!";
    }

    @GetMapping("/bye")
    public String getByeMessage(){
        return "Bye!!!";
    }

    @GetMapping("/morning")
    public String getMorningMessage(){
        return "Good Morning!!!";
    }
}
