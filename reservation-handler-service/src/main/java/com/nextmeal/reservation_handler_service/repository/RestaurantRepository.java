package com.nextmeal.reservation_handler_service.repository;

import com.nextmeal.reservation_handler_service.model.jpa.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, String> {}