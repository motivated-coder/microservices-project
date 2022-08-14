package com.skd.controller;

import com.skd.client.fraud.FraudCheckResponse;
import com.skd.dto.FraudCheckRequest;
import com.skd.entity.FraudCheckHistory;
import com.skd.service.FraudCheckService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/fraud/api/v1")
public class FraudCheckController {
    @Autowired
    FraudCheckService fraudCheckService;

    @PostMapping("/register")
    public ResponseEntity<?> registerFraud(@Valid @RequestBody FraudCheckRequest fraudCheckRequest) {
        FraudCheckHistory fraudCheckHistory = fraudCheckService.register(fraudCheckRequest);
        log.info("{} registered in the FraudCheckHistory database",fraudCheckHistory);
        HttpHeaders headers = new HttpHeaders();
        headers.add("location", "/fraud/api/v1/" + fraudCheckHistory.getId());
        return new ResponseEntity<>(fraudCheckHistory,headers, HttpStatus.CREATED);
    }

    @GetMapping("/{email}")
    public FraudCheckResponse checkFraud(@PathVariable String email) {
        return fraudCheckService.getFraudCheckHistoryByEmail(email);
    }

    @PutMapping
    public ResponseEntity<?> editRecord(@RequestBody FraudCheckHistory fraudCheckHistory) throws Exception {
        fraudCheckService.editRecord(fraudCheckHistory);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/admin/{id}")
    public FraudCheckResponse isFraud(@PathVariable("id") Integer id) {
        return fraudCheckService.getFraudCheckHistoryByID(id);
    }

    @GetMapping("/admin/all")
    public List<FraudCheckHistory> getAllRecords() {
        return fraudCheckService.getAllRecords();
    }
}
