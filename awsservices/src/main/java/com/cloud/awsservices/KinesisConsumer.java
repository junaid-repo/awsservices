package com.cloud.awsservices;

import org.springframework.scheduling.annotation.Scheduled;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.services.kinesis.KinesisClient;
import software.amazon.awssdk.services.kinesis.model.GetRecordsRequest;
import software.amazon.awssdk.services.kinesis.model.GetRecordsResponse;
import software.amazon.awssdk.services.kinesis.model.GetShardIteratorRequest;
import software.amazon.awssdk.services.kinesis.model.GetShardIteratorResponse;
import software.amazon.awssdk.services.kinesis.model.ShardIteratorType;
import software.amazon.awssdk.services.kinesis.model.Record;
import software.amazon.awssdk.regions.Region;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class KinesisConsumer {
    private static final String STREAM_NAME = "FirstDataStream"; // your stream name
    private static final String ACCESS_KEY = "AKIAVHWHGB2PGYH6YG4S";
    private static final String SECRET_KEY = "T0eEu+uPnS4tdr4JWtQscwFnYBmUaidCOxHsFoo7";
    private KinesisClient kinesisClient;

    @PostConstruct
    public void init() {
        System.out.println("Initializing Kinesis Consumer...");

        AwsBasicCredentials awsCreds = AwsBasicCredentials.create(ACCESS_KEY, SECRET_KEY);

        kinesisClient = KinesisClient.builder()
                .region(Region.EU_NORTH_1) // Set to your region
                .credentialsProvider(StaticCredentialsProvider.create(awsCreds))
                .build();
    }


    @Scheduled(fixedDelay = 500000)
    public void consume(){
        System.out.println("Starting to consume messages from Kinesis stream...");
        try {
            consumeMessages();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println("Finished consuming messages from Kinesis stream.");
    }// Adjust the delay as needed

    public void consumeMessages() {
        String shardIterator = getShardIterator();

        while (true) { // Continuous listening loop
            GetRecordsRequest recordsRequest = GetRecordsRequest.builder()
                    .shardIterator(shardIterator)
                    .limit(10) // Adjust the limit as needed
                    .build();

            GetRecordsResponse recordsResponse = kinesisClient.getRecords(recordsRequest);
            List<Record> records = recordsResponse.records();

            for (Record record : records) {
                String message = StandardCharsets.UTF_8.decode(record.data().asByteBuffer()).toString();
                System.out.println("Received: " + message);
            }

            shardIterator = recordsResponse.nextShardIterator();

            // Sleep for a short duration to avoid excessive polling
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Consumer interrupted: " + e.getMessage());
                break;
            }
        }
    }

    private String getShardIterator() {
        GetShardIteratorRequest iteratorRequest = GetShardIteratorRequest.builder()
                .streamName(STREAM_NAME)
                .shardId("shardId-000000000000") // Replace with your shard ID
                .shardIteratorType(ShardIteratorType.LATEST)
                .build();

        GetShardIteratorResponse iteratorResponse = kinesisClient.getShardIterator(iteratorRequest);
        return iteratorResponse.shardIterator();
    }

    @PreDestroy
    public void cleanup() {
        if (kinesisClient != null) {
            kinesisClient.close();
        }
    }
}