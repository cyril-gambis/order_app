package com.orderservice.integrationtest.database;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import com.orderservice.integrationtest.config.EmbeddedMongoConfig;
import com.orderservice.product.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
@ExtendWith(SpringExtension.class)
@Import(EmbeddedMongoConfig.class)
public class ProductDatabaseIntegrationTest {

    @Test
    public void givenNewProduct_WhenSavingAndReading_ThenFound(@Autowired MongoTemplate mongoTemplate) {

        // Generic Mongo DBObject
//        DBObject product = BasicDBObjectBuilder.start()
//                .add("name", "P1")
//                .get();

        Product product = new Product(null, "P1");

        mongoTemplate.save(product, "products");
        List<Product> products = mongoTemplate.findAll(Product.class, "products");

        assertThat(products).extracting("name").containsOnly("P1");
    }

}
