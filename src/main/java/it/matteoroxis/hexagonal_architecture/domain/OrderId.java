package it.matteoroxis.hexagonal_architecture.domain;

import java.util.Objects;
import java.util.UUID;

public record OrderId(String value) {
    public OrderId {
        Objects.requireNonNull(value, "OrderId cannot be null");
    }

    public static OrderId generate() {
        return new OrderId(UUID.randomUUID().toString());
    }
}
