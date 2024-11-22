package com.nextmeal.reservation_queue_service.service;

import com.nextmeal.reservation_queue_service.model.ReservationRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import static com.nextmeal.reservation_queue_service.utils.Constants.*;

@Service
public class RequestService {

    private final RabbitTemplate rabbitTemplate;
    private static final Logger logger = LoggerFactory.getLogger(RequestService.class);

    public RequestService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendRequest(ReservationRequest request) {
        try {
            rabbitTemplate.convertAndSend(QUEUE_NAME, request);
            logger.info("Published {} to the queue " + QUEUE_NAME, request.toString());
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
}