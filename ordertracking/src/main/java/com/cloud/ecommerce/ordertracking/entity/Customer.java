package com.cloud.ecommerce.ordertracking.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;


    @NotBlank(message = "Name is mandatory")
    private String name;
    private String email;
    @NotBlank(message = "Phone number is mandatory")
    private String phoneNumber;

    @NotBlank(message = "Address is mandatory")
    private String address;
    private String city;
    @NotNull(message = "Pincode is mandatory")
    private Long pincode;
    private String state;
}
