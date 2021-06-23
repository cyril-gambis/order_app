package com.orderservice.product.web;

import com.orderservice.product.Product;
import com.orderservice.product.ProductRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping
    public Flux<Product> findAll() {
        return productRepository.findAll();
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Product>> findById(@PathVariable String id) {
        return productRepository.findById(id)
                .map(product -> ResponseEntity.ok(product))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ResponseEntity<Product>> create(@RequestBody Product product, UriComponentsBuilder uriComponentsBuilder) {

        return productRepository.save(product)
                .map(savedProduct -> {
                    URI location = uriComponentsBuilder.pathSegment("/products/{id}")
                            .buildAndExpand(savedProduct.id())
                            .toUri();
                    return ResponseEntity.created(location).body(savedProduct);
                });
    }

}
