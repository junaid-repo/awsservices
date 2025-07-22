package com.cloud.ecommerce.ordertracking.controller;


import com.cloud.ecommerce.ordertracking.entity.Customer;
import com.cloud.ecommerce.ordertracking.entity.CustomerOrder;
import com.cloud.ecommerce.ordertracking.service.EcommService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/ecomm/ordertracking")
public class Controller {

    @Autowired
    EcommService serv;

    @PostMapping("/add/customer")
    public ResponseEntity<String> addCustomer(@Valid @RequestBody Customer customer) {
        try {
            String message = serv.addCustomer(customer);
            return ResponseEntity.status(HttpStatus.CREATED).body(message);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error adding customer: " + e.getMessage());
        }

    }
    @PostMapping("/add/order")
    public ResponseEntity<String> addOrder(@RequestBody CustomerOrder order) throws JsonProcessingException {
        // Placeholder for order addition logic
        String response=serv.addOrder(order);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @PostMapping("/update/order/status")
    public ResponseEntity<String> addOrder(@RequestParam String orderId, @RequestParam String status) {
        // Placeholder for order addition logic
        String response=serv.updateOrderStatus(orderId, status);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
