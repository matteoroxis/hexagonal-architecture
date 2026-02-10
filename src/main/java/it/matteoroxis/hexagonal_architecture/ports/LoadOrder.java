package it.matteoroxis.hexagonal_architecture.ports;

import it.matteoroxis.hexagonal_architecture.domain.Order;
import it.matteoroxis.hexagonal_architecture.domain.OrderId;

import java.util.Optional;

public interface LoadOrder {
    Optional<Order> byId(OrderId id);
}
