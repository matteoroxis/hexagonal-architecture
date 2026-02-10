package it.matteoroxis.hexagonal_architecture.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class MoneyTest {

    @Test
    void shouldCreateMoneyWithValidAmount() {
        // Given
        BigDecimal amount = BigDecimal.valueOf(100.50);

        // When
        Money money = new Money(amount);

        // Then
        assertNotNull(money);
        assertEquals(amount, money.amount());
    }

    @Test
    void shouldThrowExceptionForNullAmount() {
        // When & Then
        assertThrows(NullPointerException.class, () -> new Money(null));
    }

    @Test
    void shouldThrowExceptionForNegativeAmount() {
        // Given
        BigDecimal negativeAmount = BigDecimal.valueOf(-10);

        // When & Then
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Money(negativeAmount)
        );
        assertEquals("Amount cannot be negative", exception.getMessage());
    }

    @Test
    void shouldAllowZeroAmount() {
        // Given
        BigDecimal zero = BigDecimal.ZERO;

        // When
        Money money = new Money(zero);

        // Then
        assertNotNull(money);
        assertEquals(zero, money.amount());
    }
}

