package it.matteoroxis.hexagonal_architecture.exception;

import it.matteoroxis.hexagonal_architecture.domain.OrderId;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(OrderId orderId) {
        super("Order not found: " + orderId.value());
    }
}
