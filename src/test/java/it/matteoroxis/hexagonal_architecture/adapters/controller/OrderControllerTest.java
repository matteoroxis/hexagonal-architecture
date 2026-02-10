package it.matteoroxis.hexagonal_architecture.adapters.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.matteoroxis.hexagonal_architecture.domain.OrderId;
import it.matteoroxis.hexagonal_architecture.exception.OrderNotFoundException;
import it.matteoroxis.hexagonal_architecture.ports.ShipOrderUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
@Import(GlobalExceptionHandler.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ShipOrderUseCase shipOrderUseCase;

    @Test
    void shouldShipOrderSuccessfully() throws Exception {
        // Given
        String orderId = "123";
        doNothing().when(shipOrderUseCase).shipOrder(any(OrderId.class));

        // When & Then
        mockMvc.perform(post("/api/orders/{orderId}/ship", orderId))
                .andExpect(status().isOk());

        verify(shipOrderUseCase, times(1)).shipOrder(new OrderId(orderId));
    }

    @Test
    void shouldReturn500WhenOrderNotFound() throws Exception {
        // Given
        String orderId = "999";
        doThrow(new OrderNotFoundException(new OrderId(orderId)))
                .when(shipOrderUseCase).shipOrder(any(OrderId.class));

        // When & Then
        mockMvc.perform(post("/api/orders/{orderId}/ship", orderId))
                .andExpect(status().isInternalServerError());

        verify(shipOrderUseCase, times(1)).shipOrder(new OrderId(orderId));
    }

    @Test
    void shouldReturn500WhenIllegalStateException() throws Exception {
        // Given
        String orderId = "123";
        doThrow(new IllegalStateException("Only paid orders can be shipped"))
                .when(shipOrderUseCase).shipOrder(any(OrderId.class));

        // When & Then
        mockMvc.perform(post("/api/orders/{orderId}/ship", orderId))
                .andExpect(status().isInternalServerError());

        verify(shipOrderUseCase, times(1)).shipOrder(new OrderId(orderId));
    }
}

