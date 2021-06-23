package com.orderservice.order;

import java.util.List;

public record Order (String id, List<LineItem> lineItems) {}
