package com.nextmeal.reservation_handler_service.controller;

import com.nextmeal.reservation_handler_service.model.ReservationRequest;
import com.nextmeal.reservation_handler_service.model.jpa.Restaurant;
import com.nextmeal.reservation_handler_service.model.jpa.User;
import com.nextmeal.reservation_handler_service.service.RestaurantService;
import com.nextmeal.reservation_handler_service.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/reservations")
public class ReservationHandlerController {

    private static final Logger logger = LoggerFactory.getLogger(ReservationHandlerController.class);
    private final UserService userService;
    private final RestaurantService restaurantService;

    public ReservationHandlerController(UserService userService, RestaurantService restaurantService) {
        this.userService = userService;
        this.restaurantService = restaurantService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createReservation(@RequestBody ReservationRequest body) {
        logger.info("Received request: {}", body);

        Optional<User> userOptional = userService.getUserById(body.getUserId());
        Optional<Restaurant> restaurantOptional = restaurantService.getRestaurantById(body.getRestaurantId());

        User user = null;
        Restaurant restaurant = null;
        String response = "User/Restaurant not found";

        if (userOptional.isPresent() && restaurantOptional.isPresent()) {
            user = userOptional.get();
            restaurant = restaurantOptional.get();
            StringBuilder sb = new StringBuilder();
            sb.append("Hello ").append(user.getName()).append(", your reservation for ")
                    .append(body.getNumOfPeople()).append(" at ")
                    .append(restaurant.getName()).append(" on ").append(body.getSlot().toString())
                    .append(" has been successfully confirmed. Thank you!");
            response = sb.toString();
        }

        return ResponseEntity.ok(response);
    }
}
