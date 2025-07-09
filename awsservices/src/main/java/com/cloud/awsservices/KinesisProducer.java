package com.cloud.awsservices;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.services.kinesis.KinesisClient;
import software.amazon.awssdk.services.kinesis.model.PutRecordRequest;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.core.SdkBytes;

import java.nio.charset.StandardCharsets;

import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.kinesis.KinesisClient;
import software.amazon.awssdk.services.kinesis.model.PutRecordRequest;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import java.nio.charset.StandardCharsets;

@Service
public class KinesisProducer {
    private static final String STREAM_NAME = "FirstDataStream"; // your stream name
    private static final String ACCESS_KEY = "AKIAVHWHGB2PGYH6YG4S";
    private static final String SECRET_KEY = "T0eEu+uPnS4tdr4JWtQscwFnYBmUaidCOxHsFoo7";

    private KinesisClient kinesisClient;

    @PostConstruct
    public void init() {
        AwsBasicCredentials awsCreds = AwsBasicCredentials.create(ACCESS_KEY, SECRET_KEY);

        kinesisClient = KinesisClient.builder()
                .region(Region.EU_NORTH_1) // Set to your region
                .credentialsProvider(StaticCredentialsProvider.create(awsCreds))
                .build();
    }

    public void sendMessage(String message) {
        PutRecordRequest request = PutRecordRequest.builder()
                .streamName(STREAM_NAME)
                .partitionKey("partition-key-1")
                .data(SdkBytes.fromString(message, StandardCharsets.UTF_8))
                .build();

        kinesisClient.putRecord(request);
        System.out.println("Sent: " + message);
    }

    @PreDestroy
    public void cleanup() {
        if (kinesisClient != null) {
            kinesisClient.close();
        }
    }}
