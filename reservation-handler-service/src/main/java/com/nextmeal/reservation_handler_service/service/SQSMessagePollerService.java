package com.nextmeal.reservation_handler_service.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.Message;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;

import java.util.List;

@Component
public class SQSMessagePollerService {

    private final QueueConsumerService queueConsumerService;
    private final SqsClient sqsClient;

    @Value("${aws.sqs.queue.url}")
    private String queueUrl;

    public SQSMessagePollerService(QueueConsumerService queueConsumerService, SqsClient sqsClient) {
        this.queueConsumerService = queueConsumerService;
        this.sqsClient = sqsClient;
    }

    @Scheduled(fixedRate = 5000)
    public void pollMessages() {
        ReceiveMessageRequest receiveMessageRequest = ReceiveMessageRequest.builder()
                .queueUrl(queueUrl)
                .maxNumberOfMessages(10)
                .waitTimeSeconds(5)
                .build();

        List<Message> messages = sqsClient.receiveMessage(receiveMessageRequest).messages();

        for (Message message : messages) {
            queueConsumerService.processMessage(message);
        }
    }
}
