package com.example.erp.domain.service;

import com.example.erp.domain.model.ArchivedOrder;
import com.example.erp.domain.model.UnprocessedOrder;
import com.example.erp.domain.repository.ArchiveRepository;
import com.example.erp.domain.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

@Service
public class OrderProcessingService {
    private final OrderRepository orderRepository;
    private final ArchiveRepository archiveRepository;
    private static final BlockingQueue<UnprocessedOrder> INCOMING_ORDER_QUEUE = new LinkedBlockingQueue<>();
    private final ExecutorService executorService = Executors.newFixedThreadPool(5);

    @Autowired
    public OrderProcessingService(OrderRepository orderRepository, ArchiveRepository archiveRepository) {
        this.orderRepository = orderRepository;
        this.archiveRepository = archiveRepository;
        startOrderProcessing();
    }

    private void startOrderProcessing() {
        for (int i = 0; i < 5; i++) {
            executorService.execute(() -> {
                while (true) {
                    try {
                        UnprocessedOrder UnprocessedOrder = INCOMING_ORDER_QUEUE.take();
                        System.out.println("Processing order: " + UnprocessedOrder);
                        Thread.sleep(1000); // Simulate processing time

                        // Update order status
                        UnprocessedOrder.setStatus("Processed");
                        orderRepository.save(UnprocessedOrder);

                        // Move to archive
                        archiveRepository.save(new ArchivedOrder(UnprocessedOrder));
                        orderRepository.delete(UnprocessedOrder);

                        // Send notification
                        System.out.println("Notification sent for order: " + UnprocessedOrder.getId());
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
            });
        }
    }

    public UnprocessedOrder saveOrder(UnprocessedOrder UnprocessedOrder) {
        UnprocessedOrder savedUnprocessedOrder = orderRepository.save(UnprocessedOrder);
        addOrderToQueue(savedUnprocessedOrder);
        return savedUnprocessedOrder;
    }

    private void addOrderToQueue(UnprocessedOrder UnprocessedOrder) {
        try {
            INCOMING_ORDER_QUEUE.put(UnprocessedOrder);
            System.out.println("Order added to queue: " + UnprocessedOrder);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public List<UnprocessedOrder> getAllOrders() {
        return orderRepository.findAll();
    }
}