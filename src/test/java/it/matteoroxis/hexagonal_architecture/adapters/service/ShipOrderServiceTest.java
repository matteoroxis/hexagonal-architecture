package it.matteoroxis.hexagonal_architecture.adapters.service;

import it.matteoroxis.hexagonal_architecture.domain.Money;
import it.matteoroxis.hexagonal_architecture.domain.Order;
import it.matteoroxis.hexagonal_architecture.domain.OrderId;
import it.matteoroxis.hexagonal_architecture.domain.OrderStatus;
import it.matteoroxis.hexagonal_architecture.exception.OrderNotFoundException;
import it.matteoroxis.hexagonal_architecture.ports.LoadOrder;
import it.matteoroxis.hexagonal_architecture.ports.SaveOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ShipOrderServiceTest {

    @Mock
    private LoadOrder loadOrder;

    @Mock
    private SaveOrder saveOrder;

    private ShipOrderService shipOrderService;

    @BeforeEach
    void setUp() {
        shipOrderService = new ShipOrderService(loadOrder, saveOrder);
    }

    @Test
    void shouldShipPaidOrder() {
        // Given
        OrderId orderId = new OrderId("123");
        Order order = new Order(orderId, OrderStatus.PAID, new Money(BigDecimal.valueOf(100)));

        when(loadOrder.byId(orderId)).thenReturn(Optional.of(order));

        // When
        shipOrderService.shipOrder(orderId);

        // Then
        assertEquals(OrderStatus.SHIPPED, order.status());
        verify(loadOrder, times(1)).byId(orderId);
        verify(saveOrder, times(1)).save(order);
    }

    @Test
    void shouldThrowExceptionWhenOrderNotFound() {
        // Given
        OrderId orderId = new OrderId("999");
        when(loadOrder.byId(orderId)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(OrderNotFoundException.class, () -> shipOrderService.shipOrder(orderId));
        verify(loadOrder, times(1)).byId(orderId);
        verify(saveOrder, never()).save(any());
    }

    @Test
    void shouldThrowExceptionWhenOrderIsNotPaid() {
        // Given
        OrderId orderId = new OrderId("123");
        Order order = new Order(orderId, OrderStatus.PENDING, new Money(BigDecimal.valueOf(100)));

        when(loadOrder.byId(orderId)).thenReturn(Optional.of(order));

        // When & Then
        assertThrows(IllegalStateException.class, () -> shipOrderService.shipOrder(orderId));
        verify(loadOrder, times(1)).byId(orderId);
        verify(saveOrder, never()).save(any());
    }
}

