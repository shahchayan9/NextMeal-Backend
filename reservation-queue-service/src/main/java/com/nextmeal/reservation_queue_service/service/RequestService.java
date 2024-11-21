package com.nextmeal.reservation_queue_service.service;

import com.nextmeal.reservation_queue_service.controller.ReservationQueueController;
import com.nextmeal.reservation_queue_service.model.ReservationRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RequestService {

    private final RestTemplate restTemplate;

    private static final Logger logger = LoggerFactory.getLogger(RequestService.class);

    public RequestService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Async
    public void sendReservationRequest(String url, ReservationRequest request) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/json");
            HttpEntity<ReservationRequest> entity = new HttpEntity<>(request, headers);
            restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    @Async
    public void sendReservationCancellationRequest(String url, ReservationRequest request) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/json");
            HttpEntity<ReservationRequest> entity = new HttpEntity<>(request, headers);
            restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
}
