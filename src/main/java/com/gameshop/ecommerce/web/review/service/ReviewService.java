package com.gameshop.ecommerce.web.review.service;

import com.gameshop.ecommerce.web.review.dao.ReviewDAO;
import com.gameshop.ecommerce.web.review.model.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ReviewService {

    private final ReviewDAO reviewDAO;

    public List<Review> getTopRateReviews() {
        Pageable topThree = PageRequest.of(0, 3, Sort.by("rate").descending());
        return reviewDAO.findAllByOrderByRateDesc(topThree);
    }
}
