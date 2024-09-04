package com.gameshop.ecommerce.web.cart.controller;

import com.gameshop.ecommerce.web.cart.model.Cart;
import com.gameshop.ecommerce.web.cart.model.CartBody;
import com.gameshop.ecommerce.web.cart.model.dto.CartDto;
import com.gameshop.ecommerce.web.cart.service.CartService;
import com.gameshop.ecommerce.web.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/carts")
public class CartController {

    private final CartService cartService;

    @CrossOrigin
    @PostMapping()
    public ResponseEntity<CartDto> addToCart(@AuthenticationPrincipal User user, @RequestBody List<CartBody> cartBodyList) {
        CartDto cart = cartService.addProductToCart(user, cartBodyList);
        return ResponseEntity.ok(cart);
    }

    @CrossOrigin
    @DeleteMapping("/clear")
    public ResponseEntity<Cart> clearCart(@AuthenticationPrincipal User user) {
        Cart cart = cartService.clearCart(user);
        return ResponseEntity.ok(cart);
    }

    @CrossOrigin
    @GetMapping()
    public ResponseEntity<CartDto> getCartDetails(@AuthenticationPrincipal User user) {
        CartDto cart = cartService.getCartDetails(user);
        return ResponseEntity.ok(cart);
    }

    @CrossOrigin
    @DeleteMapping()
    public ResponseEntity<Cart> removeFromCart(@AuthenticationPrincipal User user, @RequestBody List<CartBody> cartBodies) {
        Cart cart = cartService.removeProductFromCart(user, cartBodies);
        return ResponseEntity.ok(cart);
    }
}
