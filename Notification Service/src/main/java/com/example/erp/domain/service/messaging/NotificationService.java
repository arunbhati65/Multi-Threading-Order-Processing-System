package com.example.erp.domain.service.messaging;

import com.example.erp.domain.model.UnprocessedOrder;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    private final JavaMailSender mailSender;

    public NotificationService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }
    @KafkaListener(topics = "processed-orders", groupId = "processed-orders", containerFactory = "kafkaListenerContainerFactory")
    public void consumeProcessedOrder(UnprocessedOrder processedOrder) {
        System.out.println("Received Notification Request: " + processedOrder);
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo("arunbhati65@gmail.com");
        mailMessage.setSubject("Order Status Update");
        mailMessage.setText("Your order with ID " + processedOrder.getId() + " is now " + processedOrder.getStatus() + ".");
        mailSender.send(mailMessage);

        System.out.println("Email sent to " + "arunbhati65@gmail.com");
    }
}