package com.jcaido.carmicroservice.service;

import com.jcaido.carmicroservice.entity.Car;

import java.util.List;

public interface CarService {
    List<Car> getAll();
    Car getCarById(int id);
    List<Car> getCarsByUserId(int userId);
    Car save(Car car);

}
