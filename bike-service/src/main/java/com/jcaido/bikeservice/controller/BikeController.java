package com.jcaido.bikeservice.controller;

import com.jcaido.bikeservice.entity.Bike;
import com.jcaido.bikeservice.service.BikeService;
import org.springframework.beans.factory.annotation.Autowired;
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
        List<Bike> bikes = bikeService.getAll();

        if (bikes.isEmpty())
            return ResponseEntity.noContent().build();

        return ResponseEntity.ok(bikes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Bike> getBikeById(@PathVariable("id") int id) {
        Bike bike = bikeService.getBikeById(id);

        if (bike == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(bike);
    }

    @GetMapping("/byuser/{userId}")
    public ResponseEntity<List<Bike>> getBikesByUserId(@PathVariable("userId") int userId) {
        List<Bike> bikes = bikeService.getBikesByUserId(userId);

        return ResponseEntity.ok(bikes);
    }

    @PostMapping
    public ResponseEntity<Bike> save(@RequestBody Bike bike) {
        return ResponseEntity.ok(bikeService.save(bike));
    }


}
