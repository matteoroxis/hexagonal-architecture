package it.matteoroxis.hexagonal_architecture.adapters.mapper;

import it.matteoroxis.hexagonal_architecture.adapters.documents.OrderDocument;
import it.matteoroxis.hexagonal_architecture.domain.Money;
import it.matteoroxis.hexagonal_architecture.domain.Order;
import it.matteoroxis.hexagonal_architecture.domain.OrderId;
import it.matteoroxis.hexagonal_architecture.domain.OrderStatus;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class OrderMapperTest {

    @Test
    void shouldMapDocumentToDomain() {
        // Given
        OrderDocument document = new OrderDocument();
        document.setId("123");
        document.setStatus("PAID");
        document.setTotalAmount(BigDecimal.valueOf(100));

        // When
        Order order = OrderMapper.toDomain(document);

        // Then
        assertNotNull(order);
        assertEquals("123", order.id().value());
        assertEquals(OrderStatus.PAID, order.status());
        assertEquals(BigDecimal.valueOf(100), order.totalAmount().amount());
    }

    @Test
    void shouldMapDomainToDocument() {
        // Given
        Order order = new Order(
                new OrderId("456"),
                OrderStatus.SHIPPED,
                new Money(BigDecimal.valueOf(200))
        );

        // When
        OrderDocument document = OrderMapper.toDocument(order);

        // Then
        assertNotNull(document);
        assertEquals("456", document.getId());
        assertEquals("SHIPPED", document.getStatus());
        assertEquals(BigDecimal.valueOf(200), document.getTotalAmount());
    }

    @Test
    void shouldPreserveDataInRoundTripConversion() {
        // Given
        Order originalOrder = new Order(
                new OrderId("789"),
                OrderStatus.PENDING,
                new Money(BigDecimal.valueOf(300))
        );

        // When
        OrderDocument document = OrderMapper.toDocument(originalOrder);
        Order convertedOrder = OrderMapper.toDomain(document);

        // Then
        assertEquals(originalOrder.id().value(), convertedOrder.id().value());
        assertEquals(originalOrder.status(), convertedOrder.status());
        assertEquals(originalOrder.totalAmount().amount(), convertedOrder.totalAmount().amount());
    }
}

