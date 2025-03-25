package com.example.erp.application.controller;


import com.example.erp.domain.model.UnprocessedOrder;
import com.example.erp.domain.service.OrderProcessingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/unprocessed-orders")
class OrderController {
    private final OrderProcessingService orderService;

    @Autowired
    public OrderController(OrderProcessingService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/add")
    public UnprocessedOrder addOrder(@RequestBody  UnprocessedOrder UnprocessedOrder) {
        return orderService.saveOrder(UnprocessedOrder);
    }

    @GetMapping("/all")
    public List<UnprocessedOrder> getAllOrders() {
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