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
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
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

        String response = "User/Restaurant not found";

        if (userOptional.isPresent() && restaurantOptional.isPresent()) {
            User user = userOptional.get();
            Restaurant restaurant = restaurantOptional.get();
            LocalDateTime slot = LocalDateTime.ofInstant(request.getSlot().toInstant(), ZoneId.systemDefault());

            if (reservationService.checkReservationAvailability(restaurant.getBusinessId(), slot, request.getNumOfPeople())) {
                Reservation reservation = new Reservation(user.getUserId(), restaurant.getBusinessId(), slot, request.getNumOfPeople(), "CONFIRMED");
                reservationService.saveReservation(reservation);

                response = "Hello " + user.getName() + ", your reservation for " +
                        request.getNumOfPeople() + " at " +
                        restaurant.getName() + " on " + request.getSlot().toString() +
                        " has been successfully confirmed. Thank you!";
            } else {
                Reservation reservation = new Reservation(user.getUserId(), restaurant.getBusinessId(), slot, request.getNumOfPeople(), "REJECTED");
                reservationService.saveReservation(reservation);

                response = "Hello " + user.getName() + ", your reservation for " +
                        request.getNumOfPeople() + " at " +
                        restaurant.getName() + " on " + request.getSlot().toString() +
                        " has been unfortunately rejected due to constraints. Please try a different slot!";
            }
        }

        return ResponseEntity.ok(response);
    }

    @PostMapping("/cancel")
    public ResponseEntity<String> cancelReservation(@RequestBody ReservationRequest request) {
        logger.info("Received cancellation request: {}", request);

        Optional<User> userOptional = userService.getUserById(request.getUserId());
        Optional<Restaurant> restaurantOptional = restaurantService.getRestaurantById(request.getRestaurantId());

        String response = "User/Restaurant not found";

        if (userOptional.isPresent() && restaurantOptional.isPresent()) {
            User user = userOptional.get();
            Restaurant restaurant = restaurantOptional.get();

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

    @GetMapping("/view/{userId}")
    public ResponseEntity<List<Reservation>> getReservationsForUser(@PathVariable String userId) {
        logger.info("Received view reservations request for: {}", userId);

        List<Reservation> reservationsByUser = null;
        Optional<User> userOptional = userService.getUserById(userId);

        if (userOptional.isPresent()) {
            reservationsByUser = reservationService.findReservationsForUser(userId);
            logger.info("Found {} reservations", reservationsByUser.size());
        }

        return ResponseEntity.ok().body(reservationsByUser);
    }
}
