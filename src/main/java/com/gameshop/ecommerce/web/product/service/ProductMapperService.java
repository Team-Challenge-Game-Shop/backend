package com.gameshop.ecommerce.web.product.service;

import com.gameshop.ecommerce.web.product.dto.ProductCatalogDTO;
import com.gameshop.ecommerce.web.product.dto.ProductDetailDTO;
import com.gameshop.ecommerce.web.product.model.Product;
import com.gameshop.ecommerce.web.product.model.ProductImage;
import com.gameshop.ecommerce.web.review.model.Review;
import com.gameshop.ecommerce.web.user.model.User;
import com.gameshop.ecommerce.web.wishlist.service.WishlistService;
import io.jsonwebtoken.lang.Arrays;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProductMapperService {
    private final WishlistService wishlistService;

    public ProductCatalogDTO toModel(Product product, User user) {
        ProductCatalogDTO productCatalogDTO = new ProductCatalogDTO();
        productCatalogDTO.setId(product.getId());
        productCatalogDTO.setName(product.getName());
        productCatalogDTO.setShortDescription(product.getShortDescription());
        productCatalogDTO.setPrice(product.getPrice());
        productCatalogDTO.setImageUrl(product.getImageUrl());
        productCatalogDTO.setCreatedAt(product.getCreatedAt());
        productCatalogDTO.setPriceWithSale(product.getPriceWithSale());
        productCatalogDTO.setBrand(product.getBrand().getName());
        if (user != null) {
            productCatalogDTO.setInWishlist(wishlistService.isProductInWishlist(product.getId(), user));
        } else {
            productCatalogDTO.setInWishlist(false);
        }
        productCatalogDTO.setRating(product.getAverageRate());
        productCatalogDTO.setReviewCount(product.getReviews().size());
        return productCatalogDTO;
    }

    public ProductDetailDTO toModelDetail(Product product, User user) {
        ProductDetailDTO productDetailDTO = new ProductDetailDTO();
        productDetailDTO.setId(product.getId());
        productDetailDTO.setName(product.getName());
        productDetailDTO.setPrice(product.getPrice());
        productDetailDTO.setImageUrl(product.getImageUrl());
        productDetailDTO.setCreatedAt(product.getCreatedAt());
        productDetailDTO.setPriceWithSale(product.getPriceWithSale());
        productDetailDTO.setBrand(product.getBrand().getName());
        productDetailDTO.setCharacteristics(filterCharacteristics(product.getCharacteristics()));
        productDetailDTO.setShortDescription(product.getShortDescription());
        productDetailDTO.setLongDescription(product.getLongDescription());
        productDetailDTO.setReviews(getReviewRates(product.getReviews()));
        productDetailDTO.setColors(getColors(product.getCharacteristics()));
        productDetailDTO.setFeatures(product.getFeatures());
        productDetailDTO.setAverageRate(product.getAverageRate());
        productDetailDTO.setCategory(product.getCategory().getName());
        if (user != null) {
            productDetailDTO.setInWishlist(wishlistService.isProductInWishlist(product.getId(), user));
        } else {
            productDetailDTO.setInWishlist(false);
        }
        if (product.getImages() != null) {
            productDetailDTO.setImages(product.getImages().stream()
                    .map(ProductImage::getUrl)
                    .toList());
        }
        return productDetailDTO;
    }

    private Map<String, String> filterCharacteristics(Map<String, String> map) {
        if (map == null || map.isEmpty()) {
            return map;
        }

        // remove Color
        return map.entrySet().stream()
                .filter(_entry -> !"Color".equals(_entry.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private List<Integer> getReviewRates(List<Review> reviews) {
        if (reviews != null) {
            return reviews.stream()
                    .map(Review::getRate)
                    .filter(Objects::nonNull)
                    .toList();
        }

        return List.of();
    }

    private List<String> getColors(Map<String, String> characteristics) {
        if (characteristics == null || !characteristics.containsKey("Color")) {
            return List.of();
        }


        final var color = characteristics.get("Color");
        final var elements = color.split(";");
        if (elements.length == 0) {
            return List.of();
        }

        return Arrays.asList(elements);
    }
}
