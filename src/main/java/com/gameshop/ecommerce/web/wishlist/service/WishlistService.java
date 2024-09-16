package com.gameshop.ecommerce.web.wishlist.service;

import com.gameshop.ecommerce.web.product.model.Product;
import com.gameshop.ecommerce.web.user.model.User;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.UUID;

public interface WishlistService {
    void addProductToWishlist(UUID productId, User user);

    @Transactional
    long removeProductFromWishlist(UUID productId, User user);

    List<Product> getUserWishlistProduct(User user);

    boolean isProductInWishlist(UUID productId, User user);
}
