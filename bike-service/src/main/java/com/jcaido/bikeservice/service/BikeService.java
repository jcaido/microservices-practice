package com.jcaido.bikeservice.service;

import com.jcaido.bikeservice.entity.Bike;
import org.springframework.stereotype.Service;

import java.util.List;

public interface BikeService {
    List<Bike> getAll();
    Bike getBikeById(int id);
    List<Bike> getBikesByUserId(int userId);
    Bike save(Bike bike);
}
