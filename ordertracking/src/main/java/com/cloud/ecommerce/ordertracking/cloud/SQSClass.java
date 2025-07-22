package com.cloud.ecommerce.ordertracking.cloud;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class SQSClass {
    @Value("${aws.sqs.queueName}")
    private String queueName;
    @Autowired
    private SqsTemplate sqsTemplate;
    private final AmazonSQS sqsClient;

    public SQSClass(AmazonSQS sqsClient) {
        this.sqsClient = sqsClient;
    }

    public void sendOrderStatus(String status) {
        String queueUrl = sqsClient.getQueueUrl(queueName).getQueueUrl();
        sqsClient.sendMessage(new SendMessageRequest(queueUrl, status));
    }

    public List<Message> getOrderStatus(){
        return sqsClient.receiveMessage(queueName).getMessages();
}
}
