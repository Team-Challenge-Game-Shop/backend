package com.gameshop.ecommerce.web.order.service;

import com.gameshop.ecommerce.utils.exception.OrderValidationException;
import com.gameshop.ecommerce.web.address.repository.AddressRepository;
import com.gameshop.ecommerce.web.order.dao.WebOrderDAO;
import com.gameshop.ecommerce.web.order.dao.WebOrderQuantityDAO;
import com.gameshop.ecommerce.web.order.model.WebOrder;
import com.gameshop.ecommerce.web.order.model.WebOrderQuantity;
import com.gameshop.ecommerce.web.product.dao.ProductDAO;
import com.gameshop.ecommerce.web.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {
    private final WebOrderDAO webOrderDAO;
    private final WebOrderQuantityDAO webOrderQuantityDAO;
    private final ProductDAO productDAO;
    private final AddressRepository addressRepository;
    private final UserRepository userService;

    @Override
    @Transactional
    public WebOrder addOrder(List<UUID> productList, List<Integer> quantityList, UUID addressId, UUID userId) {
        if (productList.isEmpty() || productList.size() != quantityList.size()) {
            throw new OrderValidationException("productList and quantityList do not match");
        }

        // address
        final var address = addressRepository.findById(addressId)
                .orElseThrow(() -> new OrderValidationException("Address not found: " + addressId));

        // user
        final var user = userService.findById(userId)
                .orElseThrow(() -> new OrderValidationException("User not found: " + userId));

        // save order
        final var webOrder = webOrderDAO.save(
                new WebOrder(null, Collections.emptyList(), user, address, Instant.now()));

        // save quantities
        final var webOrderQuantities = new ArrayList<WebOrderQuantity>();
        for (int i = 0; i < productList.size(); i++) {
            final var webOrderQuantity = new WebOrderQuantity();
            final var productId = productList.get(i);
            final var product = productDAO.findById(productId)
                    .orElseThrow(() -> new OrderValidationException("Product not found: " + productId));
            webOrderQuantity.setProduct(product);
            webOrderQuantity.setQuantity(quantityList.get(i));
            webOrderQuantity.setOrder(webOrder);

            webOrderQuantities.add(webOrderQuantityDAO.save(webOrderQuantity));
        }

        webOrder.setQuantities(webOrderQuantities);

        return webOrder;
    }

    @Override
    public Optional<WebOrder> getOrder(UUID orderId) {
        return webOrderDAO.findById(orderId);
    }
}
