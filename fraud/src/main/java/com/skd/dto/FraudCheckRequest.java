package com.skd.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Component
public class FraudCheckRequest {
    @NotNull
    private String email;
    private Boolean isFraud;

    public FraudCheckRequest(String email) {
        this.email = email;
    }
}
