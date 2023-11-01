package com.jcaido.user_microservice.feignclients;

import com.jcaido.user_microservice.models.BikeFeign;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@FeignClient(name = "bike-service", path = ("/bike"))
public interface BikeFeignClient {

    @PostMapping()
    BikeFeign save(@RequestBody BikeFeign bike);

    @GetMapping("/byuser/{userId}")
    List<BikeFeign> getBikesByUserId(@PathVariable("userId") int userId);
}
