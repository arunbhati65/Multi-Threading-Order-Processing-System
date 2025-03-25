package com.example.erp.application.controller;


import com.example.erp.domain.model.ArchivedOrder;
import com.example.erp.domain.model.UnprocessedOrder;
import com.example.erp.domain.service.ArchivedOrderService;
import com.example.erp.domain.service.OrderProcessingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/archived-orders")
class ArchiveController {
    private final ArchivedOrderService archivedOrderService;

    @Autowired
    public ArchiveController(ArchivedOrderService archivedOrderService) {
        this.archivedOrderService = archivedOrderService;
    }


    @GetMapping("/all")
    public List<ArchivedOrder> getAllOrders() {
        return archivedOrderService.getAllOrders();
    }

}