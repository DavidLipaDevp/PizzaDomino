package com.rocket.Domino.service;

import com.rocket.Domino.persistence.entity.CustomerEntity;
import com.rocket.Domino.persistence.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    private final CustomerRepository repository;

    @Autowired
    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    public CustomerEntity findByPhone(String phone){
        return this.repository.findByPhone(phone);
    }
}
