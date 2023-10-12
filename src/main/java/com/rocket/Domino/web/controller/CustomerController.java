package com.rocket.Domino.web.controller;

import com.rocket.Domino.persistence.entity.CustomerEntity;
import com.rocket.Domino.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/phone/{phone}")
    public ResponseEntity<CustomerEntity> findByPhone(@PathVariable String phone){
        return ResponseEntity.ok(this.customerService.findByPhone(phone));
    }
}
