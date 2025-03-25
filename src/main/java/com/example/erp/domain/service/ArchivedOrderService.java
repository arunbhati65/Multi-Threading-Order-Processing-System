package com.example.erp.domain.service;

import com.example.erp.domain.model.ArchivedOrder;
import com.example.erp.domain.model.UnprocessedOrder;
import com.example.erp.domain.repository.ArchiveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArchivedOrderService {

    private final ArchiveRepository archiveRepository;

    @Autowired
    public ArchivedOrderService(ArchiveRepository archiveRepository) {
        this.archiveRepository = archiveRepository;
    }


    public ArchivedOrder saveOrder(ArchivedOrder archivedOrder) {
        return archiveRepository.save(archivedOrder);
    }


    public List<ArchivedOrder> getAllOrders() {
        return archiveRepository.findAll();
    }
}