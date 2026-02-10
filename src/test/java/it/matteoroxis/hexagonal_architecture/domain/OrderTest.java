package it.matteoroxis.hexagonal_architecture.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    @Test
    void shouldCreateOrderWithValidData() {
        // Given
        OrderId orderId = new OrderId("123");
        Money money = new Money(BigDecimal.valueOf(100));
        OrderStatus status = OrderStatus.PENDING;

        // When
        Order order = new Order(orderId, status, money);

        // Then
        assertNotNull(order);
        assertEquals(orderId, order.id());
        assertEquals(status, order.status());
        assertEquals(money, order.totalAmount());
    }

    @Test
    void shouldMarkOrderAsPaid() {
        // Given
        Order order = new Order(
                new OrderId("123"),
                OrderStatus.PENDING,
                new Money(BigDecimal.valueOf(100))
        );

        // When
        order.markAsPaid();

        // Then
        assertEquals(OrderStatus.PAID, order.status());
    }

    @Test
    void shouldThrowExceptionWhenMarkingNonPendingOrderAsPaid() {
        // Given
        Order order = new Order(
                new OrderId("123"),
                OrderStatus.PAID,
                new Money(BigDecimal.valueOf(100))
        );

        // When & Then
        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                order::markAsPaid
        );
        assertEquals("Only pending orders can be marked as paid", exception.getMessage());
    }

    @Test
    void shouldMarkPaidOrderAsShipped() {
        // Given
        Order order = new Order(
                new OrderId("123"),
                OrderStatus.PAID,
                new Money(BigDecimal.valueOf(100))
        );

        // When
        order.markAsShipped();

        // Then
        assertEquals(OrderStatus.SHIPPED, order.status());
    }

    @Test
    void shouldThrowExceptionWhenMarkingNonPaidOrderAsShipped() {
        // Given
        Order order = new Order(
                new OrderId("123"),
                OrderStatus.PENDING,
                new Money(BigDecimal.valueOf(100))
        );

        // When & Then
        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                order::markAsShipped
        );
        assertEquals("Only paid orders can be shipped", exception.getMessage());
    }
}

