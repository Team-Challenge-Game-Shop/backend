package com.gameshop.ecommerce.web.wishlist.controller;

import com.gameshop.ecommerce.utils.MessageResponse;
import com.gameshop.ecommerce.utils.exception.ProductAlreadyExistException;
import com.gameshop.ecommerce.utils.exception.ProductNotFoundException;
import com.gameshop.ecommerce.web.product.dto.ProductCatalogDTO;
import com.gameshop.ecommerce.web.product.mapper.ProductMapper;
import com.gameshop.ecommerce.web.user.model.User;
import com.gameshop.ecommerce.web.wishlist.service.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/wishlist")
public class WishlistController {
    private final WishlistService wishlistService;
    private final ProductMapper productMapper;

    @PostMapping("/add/{productId}")
    public ResponseEntity<String> addProductToWishlist(@PathVariable UUID productId,
                                                       @AuthenticationPrincipal User user) {
        wishlistService.addProductToWishlist(productId, user);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Product added to wishlist " + user.getEmail());
    }

    @DeleteMapping("/remove/{productId}")
    public ResponseEntity<String> removeProductFromWishlist(@PathVariable UUID productId,
                                                            @AuthenticationPrincipal User user) {
        final var rows = wishlistService.removeProductFromWishlist(productId, user);

        return ResponseEntity.status(rows > 0 ? HttpStatus.NO_CONTENT : HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/list")
    public ResponseEntity<List<ProductCatalogDTO>> getWishlist(@AuthenticationPrincipal User user) {
        final var wishList = wishlistService.getUserWishlistProduct(user);

        final var dtoList = productMapper.productsToProductCatalogDTOs(wishList);

        return ResponseEntity.ok(dtoList);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<MessageResponse> handleProductNotFoundException() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new MessageResponse("Product not found"));
    }

    @ExceptionHandler(ProductAlreadyExistException.class)
    public ResponseEntity<MessageResponse> handleProductAlreadyInWishListException() {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new MessageResponse("Product already in wishlist"));
    }
}
