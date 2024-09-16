package com.gameshop.ecommerce.web.wishlist.service;

import com.gameshop.ecommerce.utils.exception.ProductAlreadyExistException;
import com.gameshop.ecommerce.utils.exception.ProductNotFoundException;
import com.gameshop.ecommerce.web.product.dao.ProductDAO;
import com.gameshop.ecommerce.web.product.model.Product;
import com.gameshop.ecommerce.web.user.model.User;
import com.gameshop.ecommerce.web.wishlist.dao.WishlistRepository;
import com.gameshop.ecommerce.web.wishlist.model.WishList;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class WishlistServiceImpl implements WishlistService {
    private final WishlistRepository wishlistRepository;
    private final ProductDAO productDAO;

    @Override
    @Transactional
    public void addProductToWishlist(UUID productId, User user) {
        Product product = productDAO.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id " + productId));

        final var foundProduct = wishlistRepository
                .findByProductIdAndUserId(productId, user.getId());

        if (foundProduct.isPresent()) {
            throw new ProductAlreadyExistException("Product already in wish list");
        }

        wishlistRepository.save(new WishList(Instant.now(), user, product));
    }

    @Transactional
    @Override
    public long removeProductFromWishlist(UUID productId, User user) {
        return wishlistRepository.deleteByProductIdAndUserId(productId, user.getId());
    }

    @Override
    public List<Product> getUserWishlistProduct(User user) {

        return wishlistRepository.findByUser(user).stream()
                .map(WishList::getProduct)
                .toList();
    }

    @Override
    public boolean isProductInWishlist(UUID productId, User user) {

        Product product = productDAO.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        return wishlistRepository.findByUser(user).stream().anyMatch(wishList -> wishList.getProduct().equals(product));
    }
}
