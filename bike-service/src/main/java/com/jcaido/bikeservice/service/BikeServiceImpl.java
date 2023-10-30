package com.jcaido.bikeservice.service;

import com.jcaido.bikeservice.entity.Bike;
import com.jcaido.bikeservice.exceptions.ResourceNotFoundException;
import com.jcaido.bikeservice.repository.BikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BikeServiceImpl implements BikeService{

    @Autowired
    BikeRepository bikeRepository;

    @Override
    public List<Bike> getAll() {
        List<Bike> bikes = bikeRepository.findAll();

        return bikes;
    }

    @Override
    public Bike getBikeById(int id) {
        Optional<Bike> bike = bikeRepository.findById(id);
        if (!bike.isPresent())
            throw new ResourceNotFoundException("Bike don't exist");

        return bike.orElseThrow();
    }

    @Override
    public List<Bike> getBikesByUserId(int userId) {

        return bikeRepository.findByUserId(userId);
    }

    @Override
    public Bike save(Bike bike) {
        return bikeRepository.save(bike);
    }
}
