package com.orderservice.product;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface ProductRepository extends ReactiveCrudRepository<Product, String> {

    @Query("{ id: { $exists: true }}")
    Flux<Product> findAllPaged(final Pageable page);

}
