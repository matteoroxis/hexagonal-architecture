package it.matteoroxis.hexagonal_architecture.adapters;

import it.matteoroxis.hexagonal_architecture.adapters.documents.OrderDocument;
import it.matteoroxis.hexagonal_architecture.adapters.repository.MongoOrderRepository;
import it.matteoroxis.hexagonal_architecture.domain.Order;
import it.matteoroxis.hexagonal_architecture.domain.OrderId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MongoLoadOrderAdapterTest {

    @Mock
    private MongoOrderRepository repository;

    private MongoLoadOrderAdapter adapter;

    @BeforeEach
    void setUp() {
        adapter = new MongoLoadOrderAdapter(repository);
    }

    @Test
    void shouldLoadOrderById() {
        // Given
        String id = "123";
        OrderDocument document = new OrderDocument();
        document.setId(id);
        document.setStatus("PAID");
        document.setTotalAmount(BigDecimal.valueOf(100));

        when(repository.findById(id)).thenReturn(Optional.of(document));

        // When
        Optional<Order> result = adapter.byId(new OrderId(id));

        // Then
        assertTrue(result.isPresent());
        assertEquals(id, result.get().id().value());
        verify(repository, times(1)).findById(id);
    }

    @Test
    void shouldReturnEmptyWhenOrderNotFound() {
        // Given
        String id = "999";
        when(repository.findById(id)).thenReturn(Optional.empty());

        // When
        Optional<Order> result = adapter.byId(new OrderId(id));

        // Then
        assertFalse(result.isPresent());
        verify(repository, times(1)).findById(id);
    }
}

