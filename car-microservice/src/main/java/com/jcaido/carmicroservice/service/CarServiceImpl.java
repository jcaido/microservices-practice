package com.jcaido.carmicroservice.service;

import com.jcaido.carmicroservice.entity.Car;
import com.jcaido.carmicroservice.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarServiceImpl implements CarService{

    @Autowired
    CarRepository carRepository;

    @Override
    public List<Car> getAll() {
        return carRepository.findAll();
    }

    @Override
    public Car getCarById(int id) {
        return carRepository.findById(id).orElseThrow();
    }

    @Override
    public Car save(Car car) {
        return carRepository.save(car);
    }
}
