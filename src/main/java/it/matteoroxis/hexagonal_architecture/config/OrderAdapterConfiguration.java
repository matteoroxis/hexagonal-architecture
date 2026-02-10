package it.matteoroxis.hexagonal_architecture.config;

import it.matteoroxis.hexagonal_architecture.adapters.MongoLoadOrderAdapter;
import it.matteoroxis.hexagonal_architecture.adapters.repository.MongoOrderRepository;
import it.matteoroxis.hexagonal_architecture.adapters.MongoSaveOrderAdapter;
import it.matteoroxis.hexagonal_architecture.ports.LoadOrder;
import it.matteoroxis.hexagonal_architecture.ports.SaveOrder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class OrderAdapterConfiguration {
    @Bean
    LoadOrder loadOrder(MongoOrderRepository repository) {
        return new MongoLoadOrderAdapter(repository);
    }

    @Bean
    SaveOrder saveOrder(MongoOrderRepository repository) {
        return new MongoSaveOrderAdapter(repository);
    }
}
