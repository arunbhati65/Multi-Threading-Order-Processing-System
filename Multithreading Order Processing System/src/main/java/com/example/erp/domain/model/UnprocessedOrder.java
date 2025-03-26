package com.example.erp.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class UnprocessedOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String orderName;
    private String status="Pending";

    public UnprocessedOrder() {}

    public UnprocessedOrder(String orderName) {
        this.orderName = orderName;
    }

}
