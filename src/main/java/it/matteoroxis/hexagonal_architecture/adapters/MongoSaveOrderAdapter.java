package it.matteoroxis.hexagonal_architecture.adapters;

import it.matteoroxis.hexagonal_architecture.adapters.documents.OrderDocument;
import it.matteoroxis.hexagonal_architecture.adapters.mapper.OrderMapper;
import it.matteoroxis.hexagonal_architecture.adapters.repository.MongoOrderRepository;
import it.matteoroxis.hexagonal_architecture.domain.Order;
import it.matteoroxis.hexagonal_architecture.ports.SaveOrder;

public class MongoSaveOrderAdapter implements SaveOrder {
    private final MongoOrderRepository repository;

    public MongoSaveOrderAdapter(MongoOrderRepository repository) {
        this.repository = repository;
    }

    @Override
    public void save(Order order) {
        OrderDocument document = OrderMapper.toDocument(order);
        repository.save(document);
    }
}
