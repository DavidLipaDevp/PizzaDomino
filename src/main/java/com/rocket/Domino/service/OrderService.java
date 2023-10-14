package com.rocket.Domino.service;

import com.rocket.Domino.persistence.entity.OrderEntity;
import com.rocket.Domino.persistence.projection.OrderSummary;
import com.rocket.Domino.persistence.repository.OrderRepository;
import com.rocket.Domino.service.dto.RandomOrderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
@Service

public class OrderService {
    private final OrderRepository repository;
    private static final String DELIVERY="D";
    private static final String CARRYOUT="C";
    private static final String ON_SITE="S";
    @Autowired
    public OrderService(OrderRepository repository) {
        this.repository = repository;
    }

    public List<OrderEntity> getAll(){
        return this.repository.findAll();
    }
    public List<OrderEntity> getTodayOrders(){
        LocalDateTime today= LocalDate.now().atTime(0,0);
        return this.repository.findAllByDateAfter(today);
    }

    public List<OrderEntity> getOutSideOrders(){
        List<String> methods= Arrays.asList(DELIVERY,CARRYOUT);
        return this.repository.findAllByMethodIn(methods);
    }

    public List<OrderEntity> getCustomerOrders(String idCustomer){
        return this.repository.findCustomerOrders(idCustomer);
    }

    public OrderSummary getSummary(int orderId){
        return this.repository.findSummary(orderId);
    }

    @Transactional
    public boolean saveRandomOrder(RandomOrderDto randomOrderDto){
        return this.repository.saveRandomOrder(randomOrderDto.getIdCustomer(),randomOrderDto.getMethod());
    }

}