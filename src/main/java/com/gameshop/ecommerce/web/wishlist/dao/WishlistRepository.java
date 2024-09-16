package com.gameshop.ecommerce.web.wishlist.dao;

import com.gameshop.ecommerce.web.user.model.User;
import com.gameshop.ecommerce.web.wishlist.model.WishList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface WishlistRepository extends JpaRepository<WishList, UUID> {


    long deleteByProductIdAndUserId(UUID productId, UUID userId);

    List<WishList> findByUser(User user);

    Optional<WishList> findByProductIdAndUserId(UUID productId, UUID userId);
}
