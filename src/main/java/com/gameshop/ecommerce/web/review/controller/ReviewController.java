package com.gameshop.ecommerce.web.review.controller;

import com.gameshop.ecommerce.web.review.model.dto.ReviewMainPageDTO;
import com.gameshop.ecommerce.web.review.model.dto.ReviewModelAssembler;
import com.gameshop.ecommerce.web.review.service.ReviewMapperService;
import com.gameshop.ecommerce.web.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewModelAssembler reviewModelAssembler;
    private final ReviewMapperService reviewMapperService;
    private final ReviewService reviewService;

    @CrossOrigin
    @GetMapping("/best-rate")
    public ResponseEntity<CollectionModel<ReviewMainPageDTO>> getBestRateReview() {
        List<ReviewMainPageDTO> reviewMainPageDTOS = reviewService.getTopRateReviews().stream().map(reviewMapperService::toModel).toList();

        CollectionModel<ReviewMainPageDTO> collectionModel = reviewModelAssembler.toCollectionModel(reviewMainPageDTOS);
        return ResponseEntity.ok(collectionModel);
    }
}
