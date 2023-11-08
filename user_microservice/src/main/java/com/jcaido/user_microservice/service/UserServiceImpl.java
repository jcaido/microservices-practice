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
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
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

    @Autowired
    CircuitBreakerFactory circuitBreakerFactory;

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

        CircuitBreaker circuit = circuitBreakerFactory.create("circuit1");
        return circuit.run(() ->
                        restTemplate.getForObject("http://car-service/car/byuser/" + userId, List.class),
                        t -> new ArrayList<Car>()
                );
    }

    @Override
    public List<Bike> getBikes(int userId) {
        Optional<User> user = userRepository.findById(userId);
        if (!user.isPresent())
            throw new ResourceNotFoundException("User don't exist");

        CircuitBreaker circuit = circuitBreakerFactory.create("circuit2");
        return circuit.run(() ->
                restTemplate.getForObject("http://bike-service/bike/byuser/" + userId, List.class),
                t -> new ArrayList<Bike>()
        );
    }

    @Override
    public CarFeign saveCar(int userId, CarFeign car) {
        Optional<User> user = userRepository.findById(userId);
        if (!user.isPresent())
            throw new ResourceNotFoundException("User don't exist");

        car.setUserId(userId);

        CircuitBreaker circuit = circuitBreakerFactory.create("circuit3");
        return circuit.run(() ->
                carFeignClient.save(car),
                t -> new CarFeign("", "",0)
        );
    }

    @Override
    public BikeFeign saveBike(int userId, BikeFeign bike) {
        Optional<User> user = userRepository.findById(userId);
        if (!user.isPresent())
            throw new ResourceNotFoundException("User don't exist");

        bike.setUserId(userId);

        CircuitBreaker circuit = circuitBreakerFactory.create("circuit4");
        return circuit.run(() ->
                bikeFeignClient.save(bike),
                t -> new BikeFeign("", "", 0)
        );
    }

    @Override
    public Map<String, Object> getUserAndVehicles(int userId) {
        Map<String, Object> result = new HashMap<>();

        Optional<User> user = userRepository.findById(userId);
        if (!user.isPresent()) {
            throw new ResourceNotFoundException("User don't exist");
        }

        result.put("User", user);

        List<CarFeign> cars = carFeignClient.getCarsByUserId(userId);
        if (cars.isEmpty())
            result.put("Cars", "that user haven't got cars");
        else
            result.put("Cars", cars);

        List<BikeFeign> bikes = bikeFeignClient.getBikesByUserId(userId);
        if (bikes.isEmpty())
            result.put("Bikes", "that user haven't got bikes");
        else
            result.put("Bikes", bikes);

        return result;

    }
}
