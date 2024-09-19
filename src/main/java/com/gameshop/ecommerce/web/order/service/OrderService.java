package com.gameshop.ecommerce.web.order.service;

import com.gameshop.ecommerce.web.order.model.WebOrder;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderService {
    WebOrder addOrder(List<UUID> productList, List<Integer> quantityList, UUID addressId, UUID userId);

    Optional<WebOrder> getOrder(UUID orderId);
}
