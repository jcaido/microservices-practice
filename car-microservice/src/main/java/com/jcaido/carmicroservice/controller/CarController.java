package com.jcaido.carmicroservice.controller;

import com.jcaido.carmicroservice.entity.Car;
import com.jcaido.carmicroservice.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.NotContextException;
import java.util.List;

@RestController
@RequestMapping("/car")
public class CarController {

    @Autowired
    CarService carService;

    @GetMapping
    public ResponseEntity<List<Car>> getAll() throws NotContextException {

        return new ResponseEntity<>(carService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Car> getCarById(@PathVariable("id") int id) {

        return new ResponseEntity<>(carService.getCarById(id), HttpStatus.OK);
    }

    @GetMapping("/byuser/{userId}")
    public ResponseEntity<List<Car>> getCarsByUserId(@PathVariable("userId") int userId) {

        return new ResponseEntity<>(carService.getCarsByUserId(userId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Car> save(@RequestBody Car car) {
        return new ResponseEntity<>(carService.save(car), HttpStatus.OK);
    }
}
