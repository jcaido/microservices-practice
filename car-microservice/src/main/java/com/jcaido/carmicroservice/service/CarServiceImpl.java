package com.jcaido.carmicroservice.service;

import com.jcaido.carmicroservice.entity.Car;
import com.jcaido.carmicroservice.exceptions.ResourceNotFoundException;
import com.jcaido.carmicroservice.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.naming.NotContextException;
import java.util.List;
import java.util.Optional;

@Service
public class CarServiceImpl implements CarService{

    @Autowired
    CarRepository carRepository;

    @Override
    public List<Car> getAll() throws NotContextException {
        List<Car> cars = carRepository.findAll();

        return cars;
    }

    @Override
    public Car getCarById(int id) {
        Optional<Car> car = carRepository.findById(id);
        if (!car.isPresent())
            throw new ResourceNotFoundException("Car don't exist");

        return car.orElseThrow();
    }

    @Override
    public List<Car> getCarsByUserId(int userId) {
        return carRepository.findByUserId(userId);
    }

    @Override
    public Car save(Car car) {
        return carRepository.save(car);
    }
}
