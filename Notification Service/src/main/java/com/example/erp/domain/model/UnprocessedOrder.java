package com.example.erp.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class UnprocessedOrder {
    private Long id;
    private String orderName;
    private String status;

    @Override
    public String toString() {
        return "UnprocessedOrder{" +
                "id=" + id +
                ", orderName='" + orderName + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
