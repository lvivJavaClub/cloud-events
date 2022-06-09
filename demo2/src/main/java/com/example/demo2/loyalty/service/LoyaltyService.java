package com.example.demo2.loyalty.service;

import java.util.UUID;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class LoyaltyService {

    public void grantBonusToCustomer(UUID customerId, Integer bonus) {
        log.info("Grant bonus [customerId={},bonus={}]", customerId, bonus);
    }

}
