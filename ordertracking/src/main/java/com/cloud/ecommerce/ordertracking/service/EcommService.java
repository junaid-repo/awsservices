package com.cloud.ecommerce.ordertracking.service;


import com.amazonaws.services.sqs.model.Message;
import com.cloud.ecommerce.ordertracking.cloud.KinesisProducer;
import com.cloud.ecommerce.ordertracking.cloud.SQSClass;
import com.cloud.ecommerce.ordertracking.entity.Customer;
import com.cloud.ecommerce.ordertracking.entity.CustomerOrder;
import com.cloud.ecommerce.ordertracking.model.OrderStatus;
import com.cloud.ecommerce.ordertracking.repos.EcommRepository;
import com.cloud.ecommerce.ordertracking.repos.OrderRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class EcommService {

    @Autowired
    EcommRepository eRepo;
    @Autowired
    OrderRepository oRepo;

    @Autowired
    KinesisProducer kProducer;
    @Autowired
    SQSClass sqs;


    public String addCustomer(Customer customer) {
        Customer createdCustomer=   eRepo.save(customer);
        return "Customer with id "+createdCustomer.getId()+" added successfully";
    }

    public String addOrder(CustomerOrder order) throws JsonProcessingException {
        CustomerOrder createdOrder = oRepo.save(order);

        var orderStatus= OrderStatus.builder().orderId(String.valueOf(createdOrder.orderId)).orderStatus("orderPlaced").build();

        ObjectMapper mapper = new ObjectMapper();
       String kMessage= mapper.writeValueAsString(orderStatus);
       log.info("the created message for the producer is "+kMessage);
       // kProducer.sendMessage(kMessage);
        sqs.sendOrderStatus(kMessage);
        return "Order with id " + createdOrder.getOrderId() + " added successfully";
    }

    public String updateOrderStatus(String orderId, String status) {


        List<Message> messageList = sqs.getOrderStatus();
    log.info("the received messages from SQS: " + messageList);
        messageList.stream().forEach(

                message -> {
                    ObjectMapper mapp = new ObjectMapper();
                    try {
                        OrderStatus orderStatus = mapp.readValue(message.getBody(), OrderStatus.class);
                        log.info("Order ID: " + orderStatus.getOrderId() + ", Status: " + orderStatus.getOrderStatus());
                        if (orderStatus.getOrderId().equals(orderId) && !orderStatus.getOrderStatus().equals(status)) {
                            orderStatus.setOrderStatus(status);
                            String updatedMessage = mapp.writeValueAsString(orderStatus);
                            log.info("Updated Order Status Message: " + updatedMessage);
                            // kProducer.sendMessage(updatedMessage);
                            sqs.sendOrderStatus(updatedMessage);
                            CustomerOrder customerOrder = oRepo.findById(Long.valueOf(orderId)).get();
                            customerOrder.setOrderStatus(status);
                            oRepo.save(customerOrder);

                        }
                    } catch (JsonProcessingException e) {
                        log.error("Error processing message: " + message.getBody(), e);
                    }

                }
        );


        CustomerOrder order = oRepo.findById(Long.parseLong(orderId))
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));
        order.setOrderStatus(status);
        oRepo.save(order);
        return "Order with id " + orderId + " updated successfully to status: " + status;

    }
}
