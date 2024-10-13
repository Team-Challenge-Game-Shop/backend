package com.gameshop.ecommerce.web.product.dto;

import com.gameshop.ecommerce.web.product.model.Feature;
import com.gameshop.ecommerce.web.review.model.dto.ReviewMainPageDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Relation(collectionRelation = "products")
public class ProductDetailDTO extends RepresentationModel<ProductDetailDTO> {
    private UUID id;
    private String name;
    private String shortDescription;
    private String longDescription;
    private Integer price;
    private String imageUrl;
    private List<String> images;
    private Instant createdAt;
    private Map<String, String> characteristics;
    private Integer priceWithSale;
    private String brand;
    private List<Integer> reviews;
    private List<Feature> features;
    private String category;
    private Double averageRate;
    private Boolean inWishlist;
    private List<String> colors;
}
