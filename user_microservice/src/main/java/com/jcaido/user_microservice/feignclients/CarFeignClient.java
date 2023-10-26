package com.jcaido.user_microservice.feignclients;

import com.jcaido.user_microservice.models.CarFeign;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "car-service", url = "http://localhost:8002", path = ("/car"))
public interface CarFeignClient {

    @PostMapping()
    CarFeign save(@RequestBody CarFeign car);
    @GetMapping("/byuser/{userId}")
    List<CarFeign> getCarsByUserId(@PathVariable("userId") int userId);
}
