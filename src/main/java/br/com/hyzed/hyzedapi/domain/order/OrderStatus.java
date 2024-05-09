package br.com.hyzed.hyzedapi.domain.order;

import lombok.Getter;

@Getter
public enum OrderStatus {

    WAITING_PAYMENT("waiting_payment"),
    PAID("paid"),
    SHIPPED("shipped"),
    DELIVERED("delivered"),
    CANCELED("canceled");

    private final String status;

    private OrderStatus(String status) {
        this.status = status;
    }

}
