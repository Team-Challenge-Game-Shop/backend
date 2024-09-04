package com.gameshop.ecommerce.web.wishlist.dao;

import com.gameshop.ecommerce.web.product.model.Product;
import com.gameshop.ecommerce.web.user.model.User;
import com.gameshop.ecommerce.web.wishlist.model.WishList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface WishlistDAO extends JpaRepository<WishList, UUID> {


    void deleteByProductAndUser(Product product, User user);

    List<WishList> findByUser(User user);
}
