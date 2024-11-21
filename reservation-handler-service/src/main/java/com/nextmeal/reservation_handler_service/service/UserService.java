package com.nextmeal.reservation_handler_service.service;

import com.nextmeal.reservation_handler_service.model.jpa.User;
import com.nextmeal.reservation_handler_service.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public Optional<User> getUserById(String userId) {
        return repository.findById(userId);
    }
}
