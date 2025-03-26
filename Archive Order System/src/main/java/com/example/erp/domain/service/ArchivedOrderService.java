package com.example.erp.domain.service;

import com.example.erp.domain.model.ArchivedOrder;
import com.example.erp.domain.model.UnprocessedOrder;
import com.example.erp.domain.repository.ArchiveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArchivedOrderService {

    private final ArchiveRepository archiveRepository;

    @KafkaListener(topics = "processed-orders", groupId = "processed-orders", containerFactory = "kafkaListenerContainerFactory")
    public void consumeProcessedOrder(UnprocessedOrder processedOrder) {
        System.out.println("Received processed order: " + processedOrder);
        ArchivedOrder archivedOrder = new ArchivedOrder(processedOrder);
        archivedOrder.setId(processedOrder.getId().toString());
        // Save processed order to MongoDB
        archiveRepository.save(archivedOrder);
    }

    @Autowired
    public ArchivedOrderService(ArchiveRepository archiveRepository) {
        this.archiveRepository = archiveRepository;
    }


    public ArchivedOrder saveOrder(ArchivedOrder archivedOrder) {
        return archiveRepository.save(archivedOrder);
    }

//
    public List<ArchivedOrder> getAllOrders() {
        return archiveRepository.findAll();
    }
}