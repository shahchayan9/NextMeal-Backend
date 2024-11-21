package com.nextmeal.reservation_handler_service.controller;

import com.nextmeal.reservation_handler_service.model.ReservationRequest;
import com.nextmeal.reservation_handler_service.model.jpa.Reservation;
import com.nextmeal.reservation_handler_service.model.jpa.Restaurant;
import com.nextmeal.reservation_handler_service.model.jpa.User;
import com.nextmeal.reservation_handler_service.service.ReservationService;
import com.nextmeal.reservation_handler_service.service.RestaurantService;
import com.nextmeal.reservation_handler_service.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/reservations")
public class ReservationHandlerController {

    private static final Logger logger = LoggerFactory.getLogger(ReservationHandlerController.class);
    private final UserService userService;
    private final RestaurantService restaurantService;
    private final ReservationService reservationService;

    public ReservationHandlerController(UserService userService, RestaurantService restaurantService, ReservationService reservationService) {
        this.userService = userService;
        this.restaurantService = restaurantService;
        this.reservationService = reservationService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createReservation(@RequestBody ReservationRequest request) {
        logger.info("Received reservation request: {}", request);

        Optional<User> userOptional = userService.getUserById(request.getUserId());
        Optional<Restaurant> restaurantOptional = restaurantService.getRestaurantById(request.getRestaurantId());

        User user = null;
        Restaurant restaurant = null;
        String response = "User/Restaurant not found";

        if (userOptional.isPresent() && restaurantOptional.isPresent()) {
            user = userOptional.get();
            restaurant = restaurantOptional.get();

            Reservation reservation = new Reservation(user.getUserId(), restaurant.getBusinessId(), LocalDateTime.ofInstant(request.getSlot().toInstant(),
                    ZoneId.systemDefault()), request.getNumOfPeople(), "CONFIRMED");
            reservationService.saveReservation(reservation);

            response = "Hello " + user.getName() + ", your reservation for " +
                    request.getNumOfPeople() + " at " +
                    restaurant.getName() + " on " + request.getSlot().toString() +
                    " has been successfully confirmed. Thank you!";
        }

        return ResponseEntity.ok(response);
    }

    @PostMapping("/cancel")
    private ResponseEntity<String> cancelReservation(@RequestBody ReservationRequest request) {
        logger.info("Received cancellation request: {}", request);

        Optional<User> userOptional = userService.getUserById(request.getUserId());
        Optional<Restaurant> restaurantOptional = restaurantService.getRestaurantById(request.getRestaurantId());

        User user = null;
        Restaurant restaurant = null;
        String response = "User/Restaurant not found";

        if (userOptional.isPresent() && restaurantOptional.isPresent()) {
            user = userOptional.get();
            restaurant = restaurantOptional.get();

            Optional<String> reservationIdOptional = reservationService.findReservationByDetails(user.getUserId(), restaurant.getBusinessId(),
                    LocalDateTime.ofInstant(request.getSlot().toInstant(), ZoneId.systemDefault()), request.getNumOfPeople());

            response = "Reservation not found";

            if (reservationIdOptional.isPresent()) {
                String reservationId = reservationIdOptional.get();

                reservationService.deleteReservationById(reservationId);

                response = "Hello " + user.getName() + ", your reservation for " +
                        request.getNumOfPeople() + " at " +
                        restaurant.getName() + " on " + request.getSlot().toString() +
                        " has been successfully cancelled. Thank you!";
            }
        }

        return ResponseEntity.ok(response);
    }
}
