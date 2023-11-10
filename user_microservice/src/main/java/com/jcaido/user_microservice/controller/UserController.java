package com.jcaido.user_microservice.controller;

import com.jcaido.user_microservice.entity.User;
import com.jcaido.user_microservice.models.Bike;
import com.jcaido.user_microservice.models.BikeFeign;
import com.jcaido.user_microservice.models.Car;
import com.jcaido.user_microservice.models.CarFeign;
import com.jcaido.user_microservice.service.UserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getAll() {

        return new ResponseEntity<>(userService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getById(@PathVariable("id") int id) {

        return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<User> save(@RequestBody User user) {


        return new ResponseEntity<>(userService.save(user), HttpStatus.OK);
    }

    @CircuitBreaker(name = "carsCB", fallbackMethod = "fallBackGetCars")
    @GetMapping("/cars/{userId}")
    public ResponseEntity<List<Car>> getCars(@PathVariable("userId") int userId) {

        return new ResponseEntity<>(userService.getCars(userId), HttpStatus.OK);
    }

    @CircuitBreaker(name = "carsCB", fallbackMethod = "fallBackSaveCar")
    @PostMapping("/savecar/{userId}")
    public ResponseEntity<CarFeign> saveCar(@PathVariable("userId") int userId, @RequestBody CarFeign car) {

        return new ResponseEntity<>(userService.saveCar(userId, car), HttpStatus.OK);
    }
    @GetMapping("/bikes/{userId}")
    public ResponseEntity<List<Bike>> getBikes(@PathVariable("userId") int userId) {

        return new ResponseEntity<>(userService.getBikes(userId), HttpStatus.OK);
    }
    @PostMapping("/savebike/{userId}")
    public ResponseEntity<BikeFeign> saveCar(@PathVariable("userId") int userId, @RequestBody BikeFeign bike) {

        return new ResponseEntity<>(userService.saveBike(userId, bike), HttpStatus.OK);
    }

    @GetMapping("/vehicles/{userId}")
    public ResponseEntity<Map<String, Object>> getVehiclesByUser(@PathVariable("userId") int userId) {

        return new ResponseEntity<>(userService.getUserAndVehicles(userId), HttpStatus.OK);
    }
}
