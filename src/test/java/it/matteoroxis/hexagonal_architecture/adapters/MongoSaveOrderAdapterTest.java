package it.matteoroxis.hexagonal_architecture.adapters;

import it.matteoroxis.hexagonal_architecture.adapters.documents.OrderDocument;
import it.matteoroxis.hexagonal_architecture.adapters.repository.MongoOrderRepository;
import it.matteoroxis.hexagonal_architecture.domain.Money;
import it.matteoroxis.hexagonal_architecture.domain.Order;
import it.matteoroxis.hexagonal_architecture.domain.OrderId;
import it.matteoroxis.hexagonal_architecture.domain.OrderStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MongoSaveOrderAdapterTest {

    @Mock
    private MongoOrderRepository repository;

    private MongoSaveOrderAdapter adapter;

    @BeforeEach
    void setUp() {
        adapter = new MongoSaveOrderAdapter(repository);
    }

    @Test
    void shouldSaveOrder() {
        // Given
        Order order = new Order(
                new OrderId("123"),
                OrderStatus.PAID,
                new Money(BigDecimal.valueOf(100))
        );

        when(repository.save(any(OrderDocument.class))).thenAnswer(i -> i.getArguments()[0]);

        // When
        adapter.save(order);

        // Then
        ArgumentCaptor<OrderDocument> captor = ArgumentCaptor.forClass(OrderDocument.class);
        verify(repository, times(1)).save(captor.capture());

        OrderDocument savedDocument = captor.getValue();
        assertEquals("123", savedDocument.getId());
        assertEquals("PAID", savedDocument.getStatus());
        assertEquals(BigDecimal.valueOf(100), savedDocument.getTotalAmount());
    }
}

