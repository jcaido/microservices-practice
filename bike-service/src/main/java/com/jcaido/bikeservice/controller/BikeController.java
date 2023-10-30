package com.jcaido.bikeservice.controller;

import com.jcaido.bikeservice.entity.Bike;
import com.jcaido.bikeservice.service.BikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bike")
public class BikeController {

    @Autowired
    BikeService bikeService;

    @GetMapping
    public ResponseEntity<List<Bike>> getAll() {

        return new ResponseEntity<>(bikeService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Bike> getBikeById(@PathVariable("id") int id) {

        return new ResponseEntity<>(bikeService.getBikeById(id), HttpStatus.OK);
    }

    @GetMapping("/byuser/{userId}")
    public ResponseEntity<List<Bike>> getBikesByUserId(@PathVariable("userId") int userId) {

        return new ResponseEntity<>(bikeService.getBikesByUserId(userId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Bike> save(@RequestBody Bike bike) {

        return new ResponseEntity<>(bikeService.save(bike), HttpStatus.OK);
    }

}
