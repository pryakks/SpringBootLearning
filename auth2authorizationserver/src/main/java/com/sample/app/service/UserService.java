package com.sample.app.service;

import java.util.List;

import com.sample.app.model.User;

public interface UserService {

    User save(User user);
    List<User> findAll();
    void delete(long id);
}
