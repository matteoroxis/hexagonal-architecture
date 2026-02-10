package it.matteoroxis.hexagonal_architecture.adapters.repository;

import it.matteoroxis.hexagonal_architecture.adapters.documents.OrderDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoOrderRepository extends MongoRepository<OrderDocument, String> {
}
