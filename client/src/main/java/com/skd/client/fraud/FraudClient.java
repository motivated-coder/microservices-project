package com.skd.client.fraud;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("fraud-check")
public interface FraudClient {

    @GetMapping(path = "/fraud/api/v1/{email}")
    public FraudCheckResponse checkFraud(@PathVariable("email") String email);
}
