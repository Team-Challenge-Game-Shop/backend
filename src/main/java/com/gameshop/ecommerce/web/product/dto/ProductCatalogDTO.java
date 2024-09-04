package com.gameshop.ecommerce.web.product.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Relation(collectionRelation = "products")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductCatalogDTO extends RepresentationModel<ProductCatalogDTO> implements Serializable {
    private UUID id;
    private String name;
    private String shortDescription;
    private Integer price;
    private String imageUrl;
    private Instant createdAt;
    private Integer priceWithSale;
    private String brand;
    private Boolean inWishlist;
    private Double rating;
    private Integer reviewCount;
}