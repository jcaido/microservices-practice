package com.jcaido.user_microservice.service;

import com.jcaido.user_microservice.entity.User;

import java.util.List;

public interface UserService {
    List<User> getAll();
    User getUserById(int id);
    User save(User user);
}
