package com.nextmeal.reservation_queue_service.controller;

import com.nextmeal.reservation_queue_service.model.ReservationRequest;
import com.nextmeal.reservation_queue_service.service.RequestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/reservations")
public class ReservationQueueController {

    @Autowired
    RequestService service;

    private static final Logger logger = LoggerFactory.getLogger(ReservationQueueController.class);

    @PostMapping("/send")
    public ResponseEntity<String> send(@RequestBody ReservationRequest request) {
        logger.info("Received request: {}", request);
        service.sendRequest(request);
        return ResponseEntity.accepted().body("Your reservation request has been placed.");
    }
}