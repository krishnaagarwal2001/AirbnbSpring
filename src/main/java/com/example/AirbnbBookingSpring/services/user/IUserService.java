package com.example.AirbnbBookingSpring.services.user;

import com.example.AirbnbBookingSpring.dtos.CreateUserRequest;
import com.example.AirbnbBookingSpring.dtos.UpdateUserRequest;
import com.example.AirbnbBookingSpring.models.User;

public interface IUserService {
    User createUser(CreateUserRequest createUserRequest);

    User updateUser(UpdateUserRequest updateUserRequest);
}
