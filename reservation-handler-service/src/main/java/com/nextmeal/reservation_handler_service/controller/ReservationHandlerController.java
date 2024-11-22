package com.nextmeal.reservation_handler_service.controller;

import com.nextmeal.reservation_handler_service.model.ReservationRequest;
import com.nextmeal.reservation_handler_service.model.jpa.Reservation;
import com.nextmeal.reservation_handler_service.service.ReservationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reservations")
public class ReservationHandlerController {

    private static final Logger logger = LoggerFactory.getLogger(ReservationHandlerController.class);
    private final ReservationService reservationService;

    public ReservationHandlerController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    // only for testing service directly
    @PostMapping("/create")
    public ResponseEntity<String> createReservation(@RequestBody ReservationRequest request) {
        logger.info("Received reservation request: {}", request);
        String response = reservationService.createReservation(request);
        return ResponseEntity.ok(response);
    }

    // only for testing service directly
    @PostMapping("/cancel")
    public ResponseEntity<String> cancelReservation(@RequestBody ReservationRequest request) {
        logger.info("Received cancellation request: {}", request);
        String response = reservationService.cancelReservation(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/view/{userId}")
    public ResponseEntity<List<Reservation>> getReservationsForUser(@PathVariable String userId) {
        logger.info("Received view reservations request for: {}", userId);
        List<Reservation> reservationsByUser = reservationService.getReservationsForUser(userId);
        return ResponseEntity.ok().body(reservationsByUser);
    }
}