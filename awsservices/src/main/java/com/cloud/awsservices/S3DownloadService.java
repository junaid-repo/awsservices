package com.cloud.awsservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

@Service
public class S3DownloadService {
    @Autowired
    private final S3Client s3Client;

    public S3DownloadService(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    public String downloadFile(String bucketName, String fileName) throws IOException {
        System.out.println(bucketName);
        System.out.println(fileName);
        GetObjectRequest getRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .build();

        //System.out.println(new String(s3Client.getObject(getRequest).readAllBytes(), StandardCharsets.UTF_8));
        return new String(s3Client.getObject(getRequest).readAllBytes(), StandardCharsets.UTF_8);
    }
}
