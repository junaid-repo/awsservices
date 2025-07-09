package com.cloud.awsservices;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;

import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class S3Config {
    private static final String STREAM_NAME = "FirstDataStream"; // your stream name
    private static final String ACCESS_KEY = "AKIAVHWHGB2PGYH6YG4S";
    private static final String SECRET_KEY = "T0eEu+uPnS4tdr4JWtQscwFnYBmUaidCOxHsFoo7";

    @Bean
    public S3Client s3Client() {
        AwsBasicCredentials awsCreds = AwsBasicCredentials.create(ACCESS_KEY, SECRET_KEY);

        return S3Client.builder()
                .region(Region.EU_NORTH_1)
                .credentialsProvider(StaticCredentialsProvider.create(awsCreds))
                .build();
    }}
