package it.matteoroxis.hexagonal_architecture.adapters.controller;

import it.matteoroxis.hexagonal_architecture.domain.OrderId;
import it.matteoroxis.hexagonal_architecture.ports.ShipOrderUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final ShipOrderUseCase shipOrderUseCase;

    public OrderController(ShipOrderUseCase shipOrderUseCase) {
        this.shipOrderUseCase = shipOrderUseCase;
    }

    @PostMapping("/{orderId}/ship")
    public ResponseEntity<Void> shipOrder(@PathVariable String orderId) {
        shipOrderUseCase.shipOrder(new OrderId(orderId));
        return ResponseEntity.ok().build();
    }
}