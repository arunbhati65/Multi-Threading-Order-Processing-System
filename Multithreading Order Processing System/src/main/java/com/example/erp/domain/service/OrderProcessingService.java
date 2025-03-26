package com.example.erp.domain.service;


import com.example.erp.domain.model.UnprocessedOrder;

import com.example.erp.domain.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

@Service
public class OrderProcessingService {
    private final OrderRepository orderRepository;
    private static final BlockingQueue<UnprocessedOrder> INCOMING_ORDER_QUEUE = new LinkedBlockingQueue<>();
    private final ExecutorService executorService = Executors.newFixedThreadPool(5);
   // private final RestTemplate restTemplate;
   private final KafkaTemplate<String, UnprocessedOrder> kafkaTemplate;

    @Autowired
    public OrderProcessingService(OrderRepository orderRepository /*,RestTemplate restTemplate*/, KafkaTemplate<String, UnprocessedOrder> kafkaTemplate) {
        this.orderRepository = orderRepository;
        this.kafkaTemplate = kafkaTemplate;
        //this.restTemplate = restTemplate;
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
                        /*
                        String response = restTemplate.postForObject(
                                "http://localhost:8081/archived-orders", // API Endpoint
                                UnprocessedOrder,                         // Archived Order Payload
                                String.class                            // Expected Response Type
                        );
                        System.out.println("Archived Order Response: " + response);
                        */
                        kafkaTemplate.send("processed-orders", UnprocessedOrder);
                        System.out.println("Published processed order to Kafka: " + UnprocessedOrder);

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