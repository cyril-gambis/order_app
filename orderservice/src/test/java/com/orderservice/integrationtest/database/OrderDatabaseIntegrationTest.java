package com.orderservice.integrationtest.database;

import com.mongodb.DBObject;
import com.orderservice.integrationtest.config.EmbeddedMongoConfig;
import com.orderservice.order.LineItem;
import com.orderservice.order.Order;
import com.orderservice.price.Currency;
import com.orderservice.price.Price;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashMap;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
@ExtendWith(SpringExtension.class)
@Import(EmbeddedMongoConfig.class)
public class OrderDatabaseIntegrationTest {

    @Test
    public void givenNewOrder_WhenSavingAndReading_ThenFound(@Autowired MongoTemplate mongoTemplate) {

        var productId = "12345";
        var collectionName = "order";
        Order order = new Order(null, List.of(new LineItem(productId, 1, new HashMap<>(), new Price(20.5, Currency.EUR))));

        mongoTemplate.save(order, collectionName);
        List<Order> orders = mongoTemplate.findAll(Order.class, collectionName);

        assertThat(orders)
                .flatExtracting("lineItems")
                .extracting("product")
                .contains(productId);
    }

}
