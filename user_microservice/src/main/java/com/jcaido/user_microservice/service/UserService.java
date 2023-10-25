package com.jcaido.user_microservice.service;

import com.jcaido.user_microservice.entity.User;
import com.jcaido.user_microservice.models.Bike;
import com.jcaido.user_microservice.models.Car;
import com.jcaido.user_microservice.models.CarFeign;

import java.util.List;

public interface UserService {
    List<User> getAll();
    User getUserById(int id);
    User save(User user);
    List<Car> getCars(int userId);
    List<Bike> getBikes(int userId);
    CarFeign saveCar(int usrId, CarFeign car);
}
