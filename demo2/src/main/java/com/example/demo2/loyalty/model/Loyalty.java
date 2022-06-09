package com.example.demo2.loyalty.model;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Loyalty {

    private UUID loyaltyId;

    private UUID customerId;

    private Integer bonus;

}
