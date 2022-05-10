package com.coupan.service.app.controller;

import com.coupan.service.app.oauth.service.Repository.UserRepository;
import com.coupan.service.app.oauth.service.model.User;
import com.coupan.service.app.security.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class UserController {

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/")
    public String showLoginPage(){
        return "login";
    }

    @PostMapping("/login")
    public String login(String email, String password){
     boolean loginResposne=   securityService.login(email,password);
     if(loginResposne){
         return "index";
     }
     return "login";
     }

     @GetMapping("/showReg")
     public String showRegistrationPAge(){
        return "registerUser";
     }

     @PostMapping("/registerUser")
     public String register(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "login";
     }
}
