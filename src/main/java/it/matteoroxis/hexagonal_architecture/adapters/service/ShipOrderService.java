package it.matteoroxis.hexagonal_architecture.adapters.service;

import it.matteoroxis.hexagonal_architecture.domain.Order;
import it.matteoroxis.hexagonal_architecture.domain.OrderId;
import it.matteoroxis.hexagonal_architecture.exception.OrderNotFoundException;
import it.matteoroxis.hexagonal_architecture.ports.LoadOrder;
import it.matteoroxis.hexagonal_architecture.ports.SaveOrder;
import it.matteoroxis.hexagonal_architecture.ports.ShipOrderUseCase;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
class ShipOrderService implements ShipOrderUseCase {
    private final LoadOrder loadOrder;
    private final SaveOrder saveOrder;

    public ShipOrderService(LoadOrder loadOrder, SaveOrder saveOrder) {
        this.loadOrder = loadOrder;
        this.saveOrder = saveOrder;
    }

    @Override
    public void shipOrder(OrderId orderId) {
        Order order = loadOrder.byId(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));

        order.markAsShipped();
        saveOrder.save(order);
    }
}
