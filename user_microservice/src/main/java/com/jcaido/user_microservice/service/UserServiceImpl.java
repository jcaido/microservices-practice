package com.jcaido.user_microservice.service;

import com.jcaido.user_microservice.entity.User;
import com.jcaido.user_microservice.models.Car;
import com.jcaido.user_microservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepository userRepository;

    @Autowired
    RestTemplate restTemplate;

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(int id) {
        return userRepository.findById(id).orElseThrow();
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<Car> getCars(int userId) {
        List<Car> cars = restTemplate.getForObject("http:/localhost:8002/byuser" + userId, List.class);

        return cars;
    }
}
