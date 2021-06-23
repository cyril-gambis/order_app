package com.orderservice.order;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface OrderRepository extends ReactiveCrudRepository<Order, String> {

    @Query("{ id: { $exists: true }}")
    Flux<Order> findAllPaged(final Pageable page);

}
