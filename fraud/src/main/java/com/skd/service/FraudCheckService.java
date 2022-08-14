package com.skd.service;

import com.skd.client.fraud.FraudCheckResponse;
import com.skd.dto.FraudCheckRequest;
import com.skd.entity.FraudCheckHistory;
import com.skd.repository.FraudCheckRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class FraudCheckService {

    @Autowired
    FraudCheckRepository fraudCheckRepository;

    public FraudCheckHistory register(FraudCheckRequest fraudCheckRequest) {
        FraudCheckHistory fraudCheckHistory = FraudCheckHistory.builder()
                .email(fraudCheckRequest.getEmail())
                .isFraud(fraudCheckRequest.getIsFraud())
                .createdAt(LocalDateTime.now())
                .build();

        return  fraudCheckRepository.save(fraudCheckHistory);
    }

    @Transactional
    public void editRecord(FraudCheckHistory fraudCheckHistory) throws Exception {
        FraudCheckHistory fch= fraudCheckRepository.findByEmail(fraudCheckHistory.getEmail()).orElseThrow(() ->new Exception("No such entity exists in record"));
            fch.setIsFraud(fraudCheckHistory.getIsFraud());
            fch.setEmail(fraudCheckHistory.getEmail());
    }

    public FraudCheckResponse getFraudCheckHistoryByID(Integer id){
        FraudCheckHistory fraudCheckHistory = fraudCheckRepository.findById(id).orElseThrow(() -> new RuntimeException("Mo Such Entity exception"));
        return new FraudCheckResponse(fraudCheckHistory.getIsFraud());
    }

    public List<FraudCheckHistory> getAllRecords(){
        List<FraudCheckHistory> allRecords = fraudCheckRepository.findAll();
        return allRecords;
    }

    public FraudCheckResponse getFraudCheckHistoryByEmail(String email){
        log.info("A new request received for fraud check for email {}", email);
        FraudCheckHistory fraudCheckHistory = fraudCheckRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Mo Such Entity exception"));
        if (fraudCheckHistory.getIsFraud()) {
            log.info("Fraud id detected");
        } else {
            log.info("Valid id detected");
        }
        return new FraudCheckResponse(fraudCheckHistory.getIsFraud());
    }
}
