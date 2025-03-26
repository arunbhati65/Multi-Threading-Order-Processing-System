package com.example.erp.application.controller;


import com.example.erp.domain.model.ArchivedOrder;
import com.example.erp.domain.model.UnprocessedOrder;
import com.example.erp.domain.repository.ArchiveRepository;
import com.example.erp.domain.service.ArchivedOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/archived-orders")
class ArchiveController {
    private final ArchivedOrderService archivedOrderService;
    private final ArchiveRepository archiveRepository;

    @Autowired
    public ArchiveController(ArchivedOrderService archivedOrderService, ArchiveRepository archiveRepository) {
        this.archivedOrderService = archivedOrderService;
        this.archiveRepository = archiveRepository;
    }

    @PostMapping
    public ResponseEntity<String> archiveOrder(@RequestBody UnprocessedOrder order) {
        ArchivedOrder archivedOrder = new ArchivedOrder(order);
        archivedOrder.setId(order.getId().toString());
        archiveRepository.save(archivedOrder);
        return ResponseEntity.ok("Order with ID " + archivedOrder.getId() + " archived successfully.");
    }

    @GetMapping("/all")
    public List<ArchivedOrder> getAllOrders() {
        return archivedOrderService.getAllOrders();
    }

}