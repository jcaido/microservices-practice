package com.jcaido.authservice.controller;

import com.jcaido.authservice.entity.UserCredential;
import com.jcaido.authservice.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public String addNewUser(@RequestBody UserCredential userCredential) {
        return authService.saveUser(userCredential);
    }

    @GetMapping("/token")
    public String getToken(UserCredential userCredential) {
        return authService.generateToken(userCredential.getName());
    }
}
