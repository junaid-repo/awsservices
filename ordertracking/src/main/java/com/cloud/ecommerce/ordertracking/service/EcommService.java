package com.cloud.ecommerce.ordertracking.service;


import com.cloud.ecommerce.ordertracking.entity.Customer;
import com.cloud.ecommerce.ordertracking.repos.EcommRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EcommService {

    @Autowired
    EcommRepository eRepo;

    public String addCustomer(Customer customer) {
        Customer createdCustomer=   eRepo.save(customer);
        return "Customer with id "+createdCustomer.getId()+" added successfully";
    }
}
