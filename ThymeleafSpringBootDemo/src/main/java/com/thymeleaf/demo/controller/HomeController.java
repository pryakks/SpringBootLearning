package com.thymeleaf.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {
    @GetMapping("/hello")
    public String hello(@RequestParam(value = "name", defaultValue = "World", required = true) String name,
                              Model model){
        model.addAttribute("name",name);
        return "hello"; //name of html page
    }
}
