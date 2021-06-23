package com.orderservice.order;

import com.orderservice.price.Price;

import java.util.Map;

public record LineItem(String product, int quantity, Map<String, Object> attributes, Price total) {}
