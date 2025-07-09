package com.cloud.awsservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/kinesis")
public class KinesisController {

    @Autowired
    S3UploadService uploadService;

    @Autowired
    S3DownloadService downloadService;

    private final KinesisProducer producer;

    public KinesisController(KinesisProducer producer) {
        this.producer = producer;
    }

    @PostMapping("/send")
    public ResponseEntity<String> sendToStream(@RequestBody String message) {
        producer.sendMessage(message);
        return ResponseEntity.ok("Message sent to Kinesis stream!");
    }

    @PostMapping("/s3/upload")
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file) {
        try {
            String url = uploadService.uploadFile(file);
            return ResponseEntity.ok("File uploaded successfully: " + url);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Upload failed: " + e.getMessage());
        }
    }

    @PostMapping("/s3/download")
    public ResponseEntity<String> download(@RequestParam("bucketName") String bucketName, @RequestParam("fileName") String fileName) throws IOException {


        String response = downloadService.downloadFile(bucketName, fileName);
        return ResponseEntity.status(HttpStatus.OK)
                .body("Download Success: " + response);

    }
}