package com.gameshop.ecommerce.web.review.dao;

import com.gameshop.ecommerce.web.review.model.Review;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ReviewDAO extends JpaRepository<Review, UUID> {
    List<Review> findAllByOrderByRateDesc(Pageable pageable);
}
