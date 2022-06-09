package com.example.demo2.loyalty.listener.dto;

import java.util.UUID;

import lombok.Data;

@Data
public class CustomerModel {

    private UUID customerId;

    private String email;

}
