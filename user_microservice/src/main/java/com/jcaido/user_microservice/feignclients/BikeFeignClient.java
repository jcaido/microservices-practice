package com.jcaido.user_microservice.feignclients;

import com.jcaido.user_microservice.models.BikeFeign;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "bike-service", url = "http://localhost:8003")
@RequestMapping("/bike")
public interface BikeFeignClient {

    @PostMapping()
    BikeFeign save(@RequestBody BikeFeign car);
}
