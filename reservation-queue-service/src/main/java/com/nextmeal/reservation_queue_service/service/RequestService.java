package com.nextmeal.reservation_queue_service.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nextmeal.reservation_queue_service.model.ReservationRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

import java.util.UUID;

import static com.nextmeal.reservation_queue_service.utils.Constants.*;

@Service
public class RequestService {

    private final SqsClient sqsClient;
    private final ObjectMapper objectMapper;

    private static final Logger logger = LoggerFactory.getLogger(RequestService.class);

    public RequestService(SqsClient sqsClient, ObjectMapper objectMapper) {
        this.sqsClient = sqsClient;
        this.objectMapper = objectMapper;
    }

    public void sendRequest(ReservationRequest request) {
        try {
            String messageBody = objectMapper.writeValueAsString(request);

            SendMessageRequest sendMessageRequest = SendMessageRequest.builder()
                            .queueUrl(QUEUE_URL).messageBody(messageBody)
                            .messageGroupId(request.getRestaurantId())
                            .messageDeduplicationId(UUID.randomUUID().toString()).build();
            sqsClient.sendMessage(sendMessageRequest);
            logger.info("Published {} to the SQS queue " + QUEUE_NAME, request.toString());
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
}