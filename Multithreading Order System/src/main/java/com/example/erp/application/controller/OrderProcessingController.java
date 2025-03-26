package com.example.erp.application.controller;


import com.example.erp.domain.model.UnprocessedOrder;
import com.example.erp.domain.service.OrderProcessingService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/orders")
class OrderProcessingController {
    private final OrderProcessingService orderService;


    @Autowired
    public OrderProcessingController(OrderProcessingService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/receive")
    public UnprocessedOrder addOrder(@RequestBody  UnprocessedOrder UnprocessedOrder) {
        return orderService.saveOrder(UnprocessedOrder);
    }

    @GetMapping("/all-unprocessed")
    public List<UnprocessedOrder> getAllProcessedOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/addHardcoded")
    public String addHardcodedOrders() {
        orderService.saveOrder(new UnprocessedOrder("Laptop"));
        orderService.saveOrder(new UnprocessedOrder("Smartphone"));
        orderService.saveOrder(new UnprocessedOrder("Tablet"));
        orderService.saveOrder(new UnprocessedOrder("Smartwatch"));
        orderService.saveOrder(new UnprocessedOrder("Headphones"));
        return "Hardcoded orders added successfully";
    }



}