package it.matteoroxis.hexagonal_architecture.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderIdTest {

    @Test
    void shouldCreateOrderIdWithValidValue() {
        // Given
        String value = "order-123";

        // When
        OrderId orderId = new OrderId(value);

        // Then
        assertNotNull(orderId);
        assertEquals(value, orderId.value());
    }

    @Test
    void shouldThrowExceptionForNullValue() {
        // When & Then
        assertThrows(NullPointerException.class, () -> new OrderId(null));
    }

    @Test
    void shouldGenerateUniqueOrderId() {
        // When
        OrderId orderId1 = OrderId.generate();
        OrderId orderId2 = OrderId.generate();

        // Then
        assertNotNull(orderId1);
        assertNotNull(orderId2);
        assertNotEquals(orderId1.value(), orderId2.value());
    }
}

