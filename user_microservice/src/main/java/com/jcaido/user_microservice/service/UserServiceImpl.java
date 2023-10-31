package com.jcaido.user_microservice.service;

import com.jcaido.user_microservice.entity.User;
import com.jcaido.user_microservice.exceptions.ResourceNotFoundException;
import com.jcaido.user_microservice.feignclients.BikeFeignClient;
import com.jcaido.user_microservice.feignclients.CarFeignClient;
import com.jcaido.user_microservice.models.Bike;
import com.jcaido.user_microservice.models.BikeFeign;
import com.jcaido.user_microservice.models.Car;
import com.jcaido.user_microservice.models.CarFeign;
import com.jcaido.user_microservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepository userRepository;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    CarFeignClient carFeignClient;

    @Autowired
    BikeFeignClient bikeFeignClient;

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(int id) {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent())
            throw new ResourceNotFoundException("User don't exist");

        return user.orElseThrow();
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<Car> getCars(int userId) {
        Optional<User> user = userRepository.findById(userId);
        if (!user.isPresent())
            throw new ResourceNotFoundException("User don't exist");

        List<Car> cars = restTemplate.getForObject("http://localhost:8002/car/byuser/" + userId, List.class);

        return cars;
    }

    @Override
    public List<Bike> getBikes(int userId) {
        Optional<User> user = userRepository.findById(userId);
        if (!user.isPresent())
            throw new ResourceNotFoundException("User don't exist");

        List<Bike> bikes = restTemplate.getForObject("http://localhost:8003/bike/byuser/" + userId, List.class);

        return bikes;
    }

    @Override
    public CarFeign saveCar(int userId, CarFeign car) {
        car.setUserId(userId);
        CarFeign carNew = carFeignClient.save(car);

        return carNew;
    }

    @Override
    public BikeFeign saveBike(int userId, BikeFeign bike) {
        bike.setUserId(userId);
        BikeFeign bikeNew = bikeFeignClient.save(bike);

        return bikeNew;
    }

    @Override
    public Map<String, Object> getUserAndVehicles(int userId) {
        Map<String, Object> result = new HashMap<>();

        Optional<User> user = userRepository.findById(userId);
        if (!user.isPresent()) {
            result.put("Mensaje", "no existe el usuario");
            return result;
        }

        result.put("User", user);

        List<CarFeign> cars = carFeignClient.getCarsByUserId(userId);
        if (cars.isEmpty())
            result.put("Cars", "ese usuario no tiene coches");
        else
            result.put("Cars", cars);

        List<BikeFeign> bikes = bikeFeignClient.getBikesByUserId(userId);
        if (bikes.isEmpty())
            result.put("Bikes", "ese usuario no tiene bikes");
        else
            result.put("Bikes", bikes);

        return result;

    }
}
