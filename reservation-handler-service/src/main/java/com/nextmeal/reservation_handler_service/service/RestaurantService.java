package com.nextmeal.reservation_handler_service.service;

import com.nextmeal.reservation_handler_service.model.jpa.Restaurant;
import com.nextmeal.reservation_handler_service.repository.RestaurantRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RestaurantService {

    private final RestaurantRepository repository;

    public RestaurantService(RestaurantRepository repository) {
        this.repository = repository;
    }

    public Optional<Restaurant> getRestaurantById(String restaurantId) {
        return repository.findById(restaurantId);
    }
}