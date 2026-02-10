package it.matteoroxis.hexagonal_architecture.ports;

import it.matteoroxis.hexagonal_architecture.domain.OrderId;

public interface ShipOrderUseCase {
    void shipOrder(OrderId orderId);
}
