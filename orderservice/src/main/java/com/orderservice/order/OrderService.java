package com.orderservice.order;

import com.orderservice.product.Product;
import com.orderservice.product.ProductRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;

/**
 * Main service that will save and validate orders
 *
 * @author Cyril Gambis
 * @date 22 juin 2021
 */
@Service
public class OrderService {

    private OrderRepository orderRepository;

    private ProductRepository productRepository;

    public OrderService(OrderRepository orderRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }


    /**
     * During the save, a validation is done on the products of the line items
     * Next step would be to manage the product references in a cache
     *
     * @author Cyril Gambis
     * @date 22 juin 2021
     */
    public Mono<Order> save(Order order) {
        // Initialize line items if null
        if (order.lineItems() == null) {
            order = new Order(order.id(), new ArrayList<>());
        }

        return Flux.fromIterable(order.lineItems())
                .map(LineItem::product)
                .flatMap(productRepository::existsById)
                //.doOnNext(System.out::println)
                .handle((bool, sink) -> {
                    if (bool) {
                        sink.next(true);
                    } else {
                        sink.error(new RuntimeException("Invalid product"));
                    }
                })
                .then(Mono.just(order))
                .flatMap(ord -> orderRepository.save(ord));
    }

    public Flux<Order> findAll() {
        return orderRepository.findAll();
    }

    public Mono<Order> findById(String id) {
        return orderRepository.findById(id);
    }

}
