package com.gameshop.ecommerce.web.product.dao;

import com.gameshop.ecommerce.web.product.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CategoryDAO extends JpaRepository<Category, UUID> {
}
