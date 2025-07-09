package com.cloud.awsservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;

@Service
public class S3UploadService {

    @Autowired
    private final S3Client s3Client;

    public S3UploadService(S3Client s3Client) {
        this.s3Client = s3Client;
    }
    // private final String bucket;



    public String uploadFile(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();

        PutObjectRequest putRequest = PutObjectRequest.builder()
                .bucket("firsts3bucket00")
                .key(fileName)
                .contentType(file.getContentType())
                .build();

        s3Client.putObject(putRequest, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));

        return "https://%s.s3.amazonaws.com/%s".formatted("firsts3bucket00", fileName);
    }
}