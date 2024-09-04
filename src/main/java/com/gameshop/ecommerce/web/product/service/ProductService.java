package com.gameshop.ecommerce.web.product.service;

import com.gameshop.ecommerce.web.order.dao.WebOrderQuantityDAO;
import com.gameshop.ecommerce.web.order.purchase.PurchaseProj;
import com.gameshop.ecommerce.web.product.dao.ProductDAO;
import com.gameshop.ecommerce.web.product.model.Product;
import com.gameshop.ecommerce.web.product.model.QProduct;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProductService {
    private static final String PREFIX = "characteristics.";
    private final ProductDAO productDAO;
    private final WebOrderQuantityDAO webOrderQuantityDAO;

    public Page<Product> getProducts(Predicate predicate, Pageable pageable, Map<String, String> allRequestParams) {
        Predicate builder = createPredicateQuery(predicate, allRequestParams);
        return productDAO.findAll(builder, pageable);
    }

    public Optional<Product> getProductById(UUID id) {
        return productDAO.findByIdCustom(id);
    }

    private Predicate createPredicateQuery(Predicate predicate, Map<String, String> allRequestParams) {
        BooleanBuilder builder = new BooleanBuilder();
        builder.or(predicate);
        allRequestParams.entrySet().stream()
                .filter(e -> e.getKey().startsWith(PREFIX))
                .forEach(e -> {
                    var key = e.getKey().substring(PREFIX.length());
                    builder.and(QProduct.product.characteristics.contains(key, e.getValue()));
                });
        return builder;
    }

    public List<Product> getMostPurchasedProducts() {
        List<PurchaseProj> productPurchases = webOrderQuantityDAO.findTopPurchasedProducts();
        List<UUID> ids = productPurchases.stream()
                .map(PurchaseProj::getId)
                .collect(Collectors.toList());

        return productDAO.findAllByIdInOrder(ids);
    }

    public List<Product> getProductsByIds(List<UUID> ids) {
        return productDAO.findAllByIdInOrder(ids);
    }

}