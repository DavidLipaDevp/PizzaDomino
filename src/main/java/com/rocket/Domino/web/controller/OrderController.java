package com.rocket.Domino.web.controller;

import com.rocket.Domino.persistence.entity.OrderEntity;
import com.rocket.Domino.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public ResponseEntity<List<OrderEntity>>  getAll(){
        return ResponseEntity.ok(this.orderService.getAll());
    }
    @GetMapping("/today")
    public ResponseEntity<List<OrderEntity>>  getTodayOrders(){
        return ResponseEntity.ok(this.orderService.getTodayOrders());
    }
    @GetMapping("/outsite")
    public ResponseEntity<List<OrderEntity>>  getOutSiteOrders(){
        return ResponseEntity.ok(this.orderService.getOutSideOrders());
    }

}