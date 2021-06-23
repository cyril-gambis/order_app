package com.orderservice.web;

import com.orderservice.order.LineItem;
import com.orderservice.order.Order;
import com.orderservice.order.OrderRepository;
import com.orderservice.order.OrderService;
import com.orderservice.order.web.OrderController;
import com.orderservice.price.Currency;
import com.orderservice.price.Price;
import com.orderservice.product.Product;
import com.orderservice.product.ProductRepository;
import com.orderservice.product.web.ProductController;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;

@WebFluxTest(OrderController.class)
@Import(OrderService.class)
public class OrderControllerTest {

    @MockBean
    private ProductRepository productRepository;

    @MockBean
    private OrderRepository orderRepository;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void givenNewOrder_WhenCreate_ThenReturned() throws Exception {

        var productId = "12345";
        var productName = "Product Name";
        var product = new Product(null, productName);

        Mockito.when(productRepository.existsById(productId)).thenReturn(Mono.just(true));

        var order = new Order(null, List.of(new LineItem(productId, 1, new HashMap<>(), new Price(20.5, Currency.EUR))));
        var orderSaved = new Order("o15554", List.of(new LineItem(productId, 1, new HashMap<>(), new Price(20.5, Currency.EUR))));

        Mockito.when(orderRepository.save(order)).thenReturn(Mono.just(orderSaved));

        webTestClient.post()
                .uri("/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(order))
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.id").isEqualTo(orderSaved.id())
//                .jsonPath("$.name").isEqualTo(productName)
                .consumeWith(System.out::println);
    }

    @Test
    public void givenBadNewOrder_WhenCreate_ThenBadRequest() throws Exception {

        Mockito.when(productRepository.findById(Mockito.anyString())).thenReturn(Mono.empty());

        var order = new Order(null, List.of(new LineItem("4034930", 1, new HashMap<>(), new Price(20.5, Currency.EUR))));
        var orderSaved = new Order("o15554", List.of(new LineItem("4034930", 1, new HashMap<>(), new Price(20.5, Currency.EUR))));

        Mockito.when(orderRepository.save(order)).thenReturn(Mono.just(orderSaved));

        webTestClient.post()
                .uri("/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(order))
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .consumeWith(System.out::println);
    }

}
