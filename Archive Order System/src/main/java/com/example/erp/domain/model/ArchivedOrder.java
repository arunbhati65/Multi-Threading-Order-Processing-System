package com.example.erp.domain.model;

import lombok.Getter;
import lombok.Setter;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Setter
@Getter
@Document
public class ArchivedOrder {
    @Id
    private String id;
    private String orderName;
    private String status;

    public ArchivedOrder() {}

    public ArchivedOrder(UnprocessedOrder UnprocessedOrder) {
        this.id=String.valueOf(UnprocessedOrder.getId());
        this.orderName = UnprocessedOrder.getOrderName();
        this.status = UnprocessedOrder.getStatus();
    }
}
