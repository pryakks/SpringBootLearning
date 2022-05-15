package com.coupan.service.app.oauth.service.config;

import com.coupan.service.app.oauth.service.security.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

//@Configuration // we are removing this for OAUTH
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
//        http.formLogin();// no need for custom login
        http.authorizeRequests()
                .mvcMatchers(HttpMethod.POST,"/register/user").permitAll()
                .mvcMatchers(HttpMethod.GET, "/couponapi/coupons","/index",
                       "/showGetCoupon","/getCoupon","/couponDetails").hasAnyRole("USER", "ADMIN")
                .mvcMatchers(HttpMethod.POST,"/getCoupon").hasAnyRole("USER","ADMIN")
                .mvcMatchers(HttpMethod.GET, "/showCreateCoupon","/createCoupon","/createResponse").hasRole("ADMIN")
                .mvcMatchers(HttpMethod.POST,"/couponapi/coupons","/saveCoupon","/getCoupon").hasRole("ADMIN")
                .mvcMatchers("/","/login", "/logout","/showReg","/registerUser").permitAll()// by default both for GET and POST
//                .mvcMatchers(HttpMethod.POST,"/","/login", "/logout","/registerUser").permitAll()
                .anyRequest().denyAll()
                .and().csrf().disable().logout().logoutSuccessUrl("/").invalidateHttpSession(false); // we can use post man also
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManager();
    }
}
