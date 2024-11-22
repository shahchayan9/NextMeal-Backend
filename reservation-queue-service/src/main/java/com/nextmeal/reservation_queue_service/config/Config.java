package com.nextmeal.reservation_queue_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;

@Configuration
public class Config {

    @Bean
    public SqsClient sqsClient() {
        return SqsClient.builder()
                .region(Region.US_EAST_1)
                .build();
    }
}