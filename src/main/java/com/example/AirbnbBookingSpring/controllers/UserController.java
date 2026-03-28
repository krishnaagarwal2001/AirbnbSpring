package com.example.AirbnbBookingSpring.controllers;

import com.example.AirbnbBookingSpring.dtos.CreateUserRequest;
import com.example.AirbnbBookingSpring.dtos.UpdateUserRequest;
import com.example.AirbnbBookingSpring.services.user.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.AirbnbBookingSpring.models.User;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody CreateUserRequest createUserRequest){
        return ResponseEntity.ok(userService.createUser(createUserRequest));
    }

    @PutMapping
    public ResponseEntity<User> updateUser(@RequestBody UpdateUserRequest updateUserRequest){
        return ResponseEntity.ok(userService.updateUser(updateUserRequest));
    }
}
