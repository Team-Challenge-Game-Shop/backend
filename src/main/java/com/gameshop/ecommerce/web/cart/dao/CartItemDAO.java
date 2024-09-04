package com.gameshop.ecommerce.web.cart.dao;

import com.gameshop.ecommerce.web.cart.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CartItemDAO extends JpaRepository<CartItem, UUID> {
}