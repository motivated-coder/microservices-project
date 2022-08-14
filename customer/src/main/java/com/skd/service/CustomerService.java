package com.skd.service;

import com.skd.client.fraud.FraudCheckResponse;
import com.skd.client.fraud.FraudClient;
import com.skd.dto.CustomerRequest;
import com.skd.entity.Customer;
import com.skd.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class CustomerService {

    CustomerRepository customerRepository;
    FraudClient fraudClient;
    @Transactional(rollbackFor = RuntimeException.class)
    public Customer registerCustomer(CustomerRequest customerRequest) {
        Customer customer = Customer.builder()
                .firstName(customerRequest.getFirstName())
                .lastName(customerRequest.getLastName())
                .email(customerRequest.getEmail())
                .build();

//        FraudCheckResponse fraudCheckResponse = restTemplate.getForObject("http://FRAUD/fraud/api/v1/{email}",
//                FraudCheckResponse.class,customer.getEmail());

        FraudCheckResponse fraudCheckResponse = fraudClient.checkFraud(customer.getEmail());
        if(fraudCheckResponse.isFraud()){
            log.error("Customer {} is fraud, aborting the execution",customer);
            throw new RuntimeException("Customer is fraud, couldn't be registered");
        }
        Customer savedCustomer = customerRepository.saveAndFlush(customer);
        log.info("Customer {} is not fraud and thus successfully registered",customer);
        return savedCustomer;
    }

    public Optional<Customer> getCustomer(Integer id) {
        return customerRepository.findById(id);
    }
}
