package com.diego.springboot.app.springbootcrud.services;

import java.util.List;

import com.diego.springboot.app.springbootcrud.entities.User;

public interface UserService {

    List<User> findAll();

    User save(User user);

    boolean ExistByUsername(String username);
}
