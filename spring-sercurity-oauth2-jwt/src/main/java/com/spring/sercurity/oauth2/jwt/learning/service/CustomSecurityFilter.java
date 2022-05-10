package com.spring.sercurity.oauth2.jwt.learning.service;


import javax.servlet.*;
import java.io.IOException;

public class CustomServiceFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("before");
        chain.doFilter(request,response);
        System.out.println("after");

    }
}
