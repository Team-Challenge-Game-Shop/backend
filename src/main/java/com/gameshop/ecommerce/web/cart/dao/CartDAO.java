package com.gameshop.ecommerce.web.cart.dao;

import com.gameshop.ecommerce.web.cart.model.Cart;
import com.gameshop.ecommerce.web.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CartDAO extends JpaRepository<Cart, UUID> {
    Optional<Cart> findByUser(User user);

    Optional<Cart> findByUser_Id(UUID id);
}
