package com.jcaido.user_microservice.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarFeign {
    private String brand;
    private String model;
    private int userId;
}
