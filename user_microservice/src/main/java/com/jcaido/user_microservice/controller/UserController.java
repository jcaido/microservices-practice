package com.jcaido.user_microservice.controller;

import com.jcaido.user_microservice.entity.User;
import com.jcaido.user_microservice.models.Car;
import com.jcaido.user_microservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getAll() {
        List<User> users = userService.getAll();

        if (users.isEmpty())
            return ResponseEntity.noContent().build();

        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getById(@PathVariable("id") int id) {
        User user = userService.getUserById(id);

        if (user == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity<User> save(@RequestBody User user) {
        return ResponseEntity.ok(userService.save(user));
    }

    @GetMapping("/cars/{userId}")
    public ResponseEntity<List<Car>> getCars(@PathVariable("userId") int userId) {
        User user = userService.getUserById(userId);
        if (user == null)
            return ResponseEntity.notFound().build();
        
        List<Car> cars = userService.getCars(userId);
        return ResponseEntity.ok(cars);
    }
}
