package com.example.AirbnbBookingSpring.services.user;

import com.example.AirbnbBookingSpring.adapters.UserAdapter;
import com.example.AirbnbBookingSpring.dtos.CreateUserRequest;
import com.example.AirbnbBookingSpring.dtos.UpdateUserRequest;
import com.example.AirbnbBookingSpring.models.User;
import com.example.AirbnbBookingSpring.repositories.writes.UserWriteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserWriteRepository userWriteRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public User createUser(CreateUserRequest createUserRequest) {
        if (userWriteRepository.findByEmail(createUserRequest.getEmail()).isPresent()) {
            throw new RuntimeException("User already exist with the emailId");
        }

        String encodedPassword = passwordEncoder.encode(createUserRequest.getPassword());

        User user = UserAdapter.createUserRequestToUser(createUserRequest, encodedPassword);

        return userWriteRepository.save(user);
    }

    @Override
    public User updateUser(UpdateUserRequest updateUserRequest) {

        User user = userWriteRepository.findByEmail(updateUserRequest.getEmail())
                .orElseThrow(()-> new RuntimeException("User does not exist with the emailId"));

        String encodedPassword = passwordEncoder.encode(updateUserRequest.getPassword());

        User updatedUser = UserAdapter.updateUserRequestToUser(user,updateUserRequest,encodedPassword);

        return userWriteRepository.save(updatedUser);
    }
}
