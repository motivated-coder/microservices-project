package com.skd.controller;

import com.skd.dto.CustomerRequest;
import com.skd.entity.Customer;
import com.skd.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/customer/api/v1")
public class CustomerController {
    @Autowired
    CustomerService customerService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody CustomerRequest customerRequest){
        Customer customer= customerService.registerCustomer(customerRequest);
        HttpHeaders headers = new HttpHeaders();
        headers.add("location","/customer/api/v1/"+customer.getId());
        return new ResponseEntity<>(headers,HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Customer>> get(@PathVariable("id") Integer id){
        Optional<Customer> customer =  customerService.getCustomer(id);
        return new ResponseEntity<>(customer,HttpStatus.OK);
    }

}
