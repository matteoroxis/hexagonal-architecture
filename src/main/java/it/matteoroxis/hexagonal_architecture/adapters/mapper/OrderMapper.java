package it.matteoroxis.hexagonal_architecture.adapters.mapper;

import it.matteoroxis.hexagonal_architecture.adapters.documents.OrderDocument;
import it.matteoroxis.hexagonal_architecture.domain.Order;
import it.matteoroxis.hexagonal_architecture.domain.Money;
import it.matteoroxis.hexagonal_architecture.domain.OrderId;
import it.matteoroxis.hexagonal_architecture.domain.OrderStatus;

public class OrderMapper {
    public static Order toDomain(OrderDocument doc) {
        return new Order(
                new OrderId(doc.getId()),
                OrderStatus.valueOf(doc.getStatus()),
                new Money(doc.getTotalAmount())
        );
    }

    public static OrderDocument toDocument(Order order) {
        return new OrderDocument(
                order.id().value(),
                order.status().name(),
                order.totalAmount().amount()
        );
    }
}
