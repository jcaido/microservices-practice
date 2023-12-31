package com.jcaido.bikeservice.exceptions;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Data
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException{

    private String resourceName;

    public ResourceNotFoundException(String resourceName) {
        super(resourceName);
        this.resourceName = resourceName;
    }
}