package com.gameshop.ecommerce.web.review.service;

import com.gameshop.ecommerce.web.review.model.Review;
import com.gameshop.ecommerce.web.review.model.dto.ReviewMainPageDTO;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class ReviewMapperService {

    public ReviewMainPageDTO toModel(Review review) {
        ReviewMainPageDTO reviewMainPageDTO = new ReviewMainPageDTO();
        reviewMainPageDTO.setId(review.getId());
        reviewMainPageDTO.setProductId(review.getProduct().getId());
        reviewMainPageDTO.setRate(review.getRate());
        reviewMainPageDTO.setComment(review.getComment());
        reviewMainPageDTO.setUserName(review.getUser().getFirstName());
        reviewMainPageDTO.setUserPhoto(review.getUser().getUserPhoto());
        return reviewMainPageDTO;
    }

    public List<ReviewMainPageDTO> toModelList(List<Review> reviews) {
        if (reviews == null || reviews.isEmpty()) {
            return Collections.emptyList();
        }

        return reviews.stream()
                .map(this::toModel)
                .toList();
    }
}
