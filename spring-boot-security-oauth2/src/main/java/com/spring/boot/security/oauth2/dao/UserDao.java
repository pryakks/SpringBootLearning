package com.spring.boot.security.oauth2.dao;

import com.spring.boot.security.oauth2.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserDao extends CrudRepository<User, Long> {
    User findByUsername(String username);
}
