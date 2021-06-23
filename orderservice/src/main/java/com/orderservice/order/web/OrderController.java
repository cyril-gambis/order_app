package com.orderservice.order.web;

import com.orderservice.order.LineItem;
import com.orderservice.order.Order;
import com.orderservice.order.OrderRepository;
import com.orderservice.order.OrderService;
import com.orderservice.price.Currency;
import com.orderservice.price.Price;
import com.orderservice.product.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/test")
    public Order test() {
        return new Order(null, List.of(new LineItem("P1", 1, new HashMap<>(), new Price(20.5, Currency.EUR))));
    }

    @GetMapping
    public Flux<Order> findAll() {
        return orderService.findAll();
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Order>> findById(@PathVariable String id) {
        return orderService.findById(id)
                .map(order -> ResponseEntity.ok(order))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    /**
     * Create orders and throws exception (service layer) if a product of the order doesn't exist in the database
     *
     * @author Cyril Gambis
     * @date 22 juin 2021
     */
    @PostMapping
    public Mono<ResponseEntity<Order>> create(@RequestBody Order order, UriComponentsBuilder uriComponentsBuilder) {
        return orderService.save(order)
                .map(savedOrder -> {
                    URI location = uriComponentsBuilder.pathSegment("/orders/{id}")
                            .buildAndExpand(savedOrder.id())
                            .toUri();
                    return ResponseEntity.created(location).body(savedOrder);
                })
                // If you want to manage the exception "by hand", but I use an exception handler
                // (with the class ExceptionHandlers)
//                .onErrorResume(e -> Mono.just(ResponseEntity.badRequest().build()))
                ;
    }

}
