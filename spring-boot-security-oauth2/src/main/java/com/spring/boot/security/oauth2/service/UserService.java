package com.spring.boot.security.oauth2.service;

import com.spring.boot.security.oauth2.model.User;

import java.util.List;


public interface UserService {

    User save(User user);
    List<User> findAll();
    void delete(long id);
}
