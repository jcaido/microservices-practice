package com.jcaido.carmicroservice.controller;

import com.jcaido.carmicroservice.entity.Car;
import com.jcaido.carmicroservice.service.CarService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/car")
public class CarController {

    @Autowired
    CarService carService;

    @GetMapping
    public ResponseEntity<List<Car>> getAll() {
        List<Car> cars = carService.getAll();

        if (cars.isEmpty())
            return ResponseEntity.noContent().build();

        return ResponseEntity.ok(cars);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Car> getCarById(@PathVariable("id") int id) {
        Car car = carService.getCarById(id);

        if (car == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(car);
    }

    @PostMapping
    public ResponseEntity<Car> save(@RequestBody Car car) {
        return ResponseEntity.ok(carService.save(car));
    }
}
