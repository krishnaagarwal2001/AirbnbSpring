package com.example.AirbnbBookingSpring.adapters;

import com.example.AirbnbBookingSpring.dtos.CreateUserRequest;
import com.example.AirbnbBookingSpring.dtos.UpdateUserRequest;
import com.example.AirbnbBookingSpring.models.User;

import java.util.UUID;

public class UserAdapter {
    public static User createUserRequestToUser(CreateUserRequest createUserRequest, String encodedPassword) {
        return User.builder()
                .email(createUserRequest.getEmail())
                .name(createUserRequest.getName())
                .password(encodedPassword)
                .build();
    }

    public static User updateUserRequestToUser(User user, UpdateUserRequest updateUserRequest, String encodedPassword) {
        user.setName(updateUserRequest.getName());
        user.setEmail(updateUserRequest.getEmail());
        user.setPassword(encodedPassword);
        return user;
    }

}
