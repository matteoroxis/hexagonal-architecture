package it.matteoroxis.hexagonal_architecture.ports;

import it.matteoroxis.hexagonal_architecture.domain.Order;

public interface SaveOrder {
    void save(Order order);
}