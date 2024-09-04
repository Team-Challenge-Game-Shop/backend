package com.gameshop.ecommerce.web.product.dao.projection.catalog;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

public interface CatalogView {
    UUID getId();

    String getName();

    String getShortDescription();

    Integer getPrice();

    String getImageUrl();

    Integer getPriceWithSale();

    Map<String, Object> getCharacteristics();

    Instant getCreatedAt();

    BrandView getBrand();

    CategoryView getCategory();

}
