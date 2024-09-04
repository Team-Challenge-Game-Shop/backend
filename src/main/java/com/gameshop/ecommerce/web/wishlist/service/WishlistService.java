package com.gameshop.ecommerce.web.wishlist.service;

import com.gameshop.ecommerce.utils.exception.ProductNotFoundException;
import com.gameshop.ecommerce.web.product.dao.ProductDAO;
import com.gameshop.ecommerce.web.product.model.Product;
import com.gameshop.ecommerce.web.user.model.User;
import com.gameshop.ecommerce.web.wishlist.dao.WishlistDAO;
import com.gameshop.ecommerce.web.wishlist.model.WishList;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class WishlistService {
    private final WishlistDAO wishlistRepository;
    private final ProductDAO productDAO;

    public void addProductToWishlist(UUID productId, User user) {
        Product product = productDAO.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id " + productId));

        wishlistRepository.save(new WishList(Instant.now(), user, product));
    }

    @Transactional
    public void removeProductFromWishlist(UUID productId, User user) {

        Product product = productDAO.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
        wishlistRepository.deleteByProductAndUser(product, user);
    }

    public List<WishList> getUserWishlist(User user) {

        return wishlistRepository.findByUser(user);
    }

    public boolean isProductInWishlist(UUID productId, User user) {

        Product product = productDAO.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        return wishlistRepository.findByUser(user).stream().anyMatch(wishList -> wishList.getProduct().equals(product));
    }
}
