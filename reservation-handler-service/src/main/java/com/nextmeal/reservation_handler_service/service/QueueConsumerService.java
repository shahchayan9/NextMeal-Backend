package com.nextmeal.reservation_handler_service.service;

import com.nextmeal.reservation_handler_service.model.ReservationRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import static com.nextmeal.reservation_handler_service.util.Constants.*;

@Service
public class QueueConsumerService {

    private final ReservationService reservationService;

    private static final Logger logger = LoggerFactory.getLogger(QueueConsumerService.class);

    public QueueConsumerService(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @RabbitListener(queues = QUEUE_NAME)
    public void handleReservation(ReservationRequest request) {
        try {
            String response;

            if (request.getRequestType().equals(REQUEST_TYPE_RESERVE)) {
                response = reservationService.createReservation(request);
            } else if (request.getRequestType().equals(REQUEST_TYPE_CANCEL)) {
                response = reservationService.cancelReservation(request);
            } else {
                response = "Invalid Request Type";
            }

            logger.info(response);
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
    }
}