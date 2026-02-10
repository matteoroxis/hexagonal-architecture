package it.matteoroxis.hexagonal_architecture.adapters;

import it.matteoroxis.hexagonal_architecture.adapters.mapper.OrderMapper;
import it.matteoroxis.hexagonal_architecture.adapters.repository.MongoOrderRepository;
import it.matteoroxis.hexagonal_architecture.domain.Order;
import it.matteoroxis.hexagonal_architecture.domain.OrderId;
import it.matteoroxis.hexagonal_architecture.ports.LoadOrder;

import java.util.Optional;

public class MongoLoadOrderAdapter implements LoadOrder {
    private final MongoOrderRepository repository;

    public MongoLoadOrderAdapter(MongoOrderRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Order> byId(OrderId id) {
        return repository.findById(id.value())
                .map(OrderMapper::toDomain);
    }
}