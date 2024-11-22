package com.nextmeal.reservation_handler_service.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.Message;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;

import java.util.List;

import static com.nextmeal.reservation_handler_service.util.Constants.QUEUE_URL;

@Component
public class SQSMessagePollerService {

    private final QueueConsumerService queueConsumerService;
    private final SqsClient sqsClient;

    public SQSMessagePollerService(QueueConsumerService queueConsumerService, SqsClient sqsClient) {
        this.queueConsumerService = queueConsumerService;
        this.sqsClient = sqsClient;
    }

    @Scheduled(fixedRate = 5000)
    public void pollMessages() {
        ReceiveMessageRequest receiveMessageRequest = ReceiveMessageRequest.builder()
                .queueUrl(QUEUE_URL)
                .maxNumberOfMessages(10)
                .waitTimeSeconds(5)
                .build();

        List<Message> messages = sqsClient.receiveMessage(receiveMessageRequest).messages();

        for (Message message : messages) {
            queueConsumerService.processMessage(message);
        }
    }
}
