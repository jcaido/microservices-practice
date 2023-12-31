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

    @CircuitBreaker(name = "bikesCB", fallbackMethod = "fallBackGetBikes")
    @GetMapping("/bikes/{userId}")
    public ResponseEntity<List<Bike>> getBikes(@PathVariable("userId") int userId) {

        return new ResponseEntity<>(userService.getBikes(userId), HttpStatus.OK);
    }

    @CircuitBreaker(name = "bikesCB", fallbackMethod = "fallBackSaveBike")
    @PostMapping("/savebike/{userId}")
    public ResponseEntity<BikeFeign> saveBike(@PathVariable("userId") int userId, @RequestBody BikeFeign bike) {

        return new ResponseEntity<>(userService.saveBike(userId, bike), HttpStatus.OK);
    }

    @CircuitBreaker(name = "vehiclesCB", fallbackMethod = "fallBackGetVehiclesByUser")
    @GetMapping("/vehicles/{userId}")
    public ResponseEntity<Map<String, Object>> getVehiclesByUser(@PathVariable("userId") int userId) {

        return new ResponseEntity<>(userService.getUserAndVehicles(userId), HttpStatus.OK);
    }

    private ResponseEntity<List<Car>> fallBackGetCars(@PathVariable("userId") int userId, RuntimeException e) {
        return new ResponseEntity("car service is not available", HttpStatus.OK);
    }

    private ResponseEntity<CarFeign> fallBackSaveCar(@PathVariable("userId") int userId, @RequestBody CarFeign car, RuntimeException e) {
        return new ResponseEntity("car service is not available", HttpStatus.OK);
    }

    private ResponseEntity<List<Bike>> fallBackGetBikes(@PathVariable("userId") int userId, RuntimeException e) {
        return new ResponseEntity("bike service is not available", HttpStatus.OK);
    }

    private ResponseEntity<BikeFeign> fallBackSaveBike(@PathVariable("userId") int userId, @RequestBody BikeFeign bike, RuntimeException e) {
        return new ResponseEntity("bike service is not available", HttpStatus.OK);
    }

    private ResponseEntity<Map<String, Object>> fallBackGetVehiclesByUser(@PathVariable("userId") int userId, RuntimeException e) {
        return new ResponseEntity("vehicles service are not available", HttpStatus.OK);
    }
}
