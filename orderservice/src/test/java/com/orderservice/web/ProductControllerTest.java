package com.orderservice.web;

import com.orderservice.product.Product;
import com.orderservice.product.ProductRepository;
import com.orderservice.product.web.ProductController;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@WebFluxTest(ProductController.class)
public class ProductControllerTest {

    @MockBean
    private ProductRepository productRepository;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void givenDefaultData_WhenReadingAll_ThenFound() throws Exception {

        var p1 = new Product(null, "P1");
        var p2 = new Product(null, "P2");
        var products = Flux.just(p1, p2);

        Mockito.when(productRepository.findAll()).thenReturn(products);

        webTestClient.get()
                .uri("/products")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Product.class)
                .contains(p1)
                .consumeWith(System.out::println);
    }

    @Test
    public void givenNewProduct_WhenCreate_ThenReturned() throws Exception {

        var productId = "12345";
        var productName = "Product Name";
        var product = new Product(null, productName);
        var productSaved = new Product(productId, productName);

        Mockito.when(productRepository.save(product)).thenReturn(Mono.just(productSaved));

        webTestClient.post()
                .uri("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(product))
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.id").isEqualTo(productId)
                .jsonPath("$.name").isEqualTo(productName)
                .consumeWith(System.out::println);
    }

    @Test
    public void givenExistingProductId_WhenFindById_ThenFound() throws Exception {

        var productId = "12345";
        var productName = "Product Name";
        var productSaved = new Product(productId, productName);

        Mockito.when(productRepository.findById(productId)).thenReturn(Mono.just(productSaved));

        webTestClient.get()
                .uri("/products/{id}", productId)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo(productId)
                .jsonPath("$.name").isEqualTo(productName)
                .consumeWith(System.out::println);
    }

    @Test
    public void givenUnknownProductId_WhenFindById_ThenNotFound() throws Exception {

        Mockito.when(productRepository.findById(Mockito.anyString())).thenReturn(Mono.empty());

        webTestClient.get()
                .uri("/products/{id}", "qsdfmklj")
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .consumeWith(System.out::println);
    }

}
