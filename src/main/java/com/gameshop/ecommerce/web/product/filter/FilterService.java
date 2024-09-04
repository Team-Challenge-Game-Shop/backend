package com.gameshop.ecommerce.web.product.filter;

import com.gameshop.ecommerce.web.product.dao.BrandDAO;
import com.gameshop.ecommerce.web.product.dao.ProductDAO;
import com.gameshop.ecommerce.web.product.model.Brand;
import com.gameshop.ecommerce.web.product.model.Product;
import com.querydsl.core.types.Predicate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class FilterService {

    private final ProductDAO productDAO;
    private final BrandDAO brandDAO;

    public FilterService(ProductDAO productDAO, BrandDAO brandDAO) {
        this.productDAO = productDAO;
        this.brandDAO = brandDAO;
    }

    public ProductFilterDTO getFilters(Predicate predicate) {
        List<Product> filteredProducts = StreamSupport.stream(productDAO.findAll(predicate).spliterator(), false)
                .toList();

        Set<String> brands = brandDAO.findAll().stream()
                .map(Brand::getName).collect(Collectors.toSet());

        Map<String, Set<String>> characteristics = new HashMap<>();
        int minPrice = Integer.MAX_VALUE;

        int maxPrice = Integer.MIN_VALUE;
        Set<Boolean> isPresentSet = new HashSet<>();
        Set<Boolean> isSaleSet = new HashSet<>();

        for (Product product : filteredProducts) {
            product.getCharacteristics().forEach((key, value) ->
                    characteristics.computeIfAbsent(key, k -> new HashSet<>()).add(String.valueOf(value)));
            int price = product.getPrice();
            if (price < minPrice) minPrice = price;
            if (price > maxPrice) maxPrice = price;

            isPresentSet.add(product.getInventory() != null && product.getInventory().getQuantity() > 0);
            isSaleSet.add(product.getPriceWithSale() != null && product.getPriceWithSale() > 0);
        }

        Map<String, Integer> prices = new HashMap<>();
        prices.put("min_price", minPrice);
        prices.put("max_price", maxPrice);
        return new ProductFilterDTO(brands, prices, characteristics, isPresentSet, isSaleSet);
    }


}
