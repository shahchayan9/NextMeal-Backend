package com.nextmeal.reservation_handler_service.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nextmeal.reservation_handler_service.model.ReservationRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.DeleteMessageRequest;
import software.amazon.awssdk.services.sqs.model.Message;

import static com.nextmeal.reservation_handler_service.util.Constants.*;

@Service
public class QueueConsumerService {

    private final ReservationService reservationService;
    private final SqsClient sqsClient;
    private final ObjectMapper objectMapper;

    private static final Logger logger = LoggerFactory.getLogger(QueueConsumerService.class);

    public QueueConsumerService(ReservationService reservationService, SqsClient sqsClient, ObjectMapper objectMapper) {
        this.reservationService = reservationService;
        this.sqsClient = sqsClient;
        this.objectMapper = objectMapper;
    }

    void processMessage(Message message) {
        try {
            ReservationRequest request = objectMapper.readValue(message.body(), ReservationRequest.class);

            String response = null;
            if (request.getRequestType().equals(REQUEST_TYPE_RESERVE)) {
                response = reservationService.createReservation(request);
            } else if (request.getRequestType().equals(REQUEST_TYPE_CANCEL)) {
                response = reservationService.cancelReservation(request);
            } else {
                response = "Invalid Request Type";
            }

            sqsClient.deleteMessage(DeleteMessageRequest.builder()
                    .queueUrl(QUEUE_URL).receiptHandle(message.receiptHandle()).build());

            logger.info(response);
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
    }
}