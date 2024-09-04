package com.gameshop.ecommerce.web.review.model.dto;

import com.gameshop.ecommerce.web.product.controller.ProductController;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ReviewModelAssembler extends RepresentationModelAssemblerSupport<ReviewMainPageDTO, ReviewMainPageDTO> {
    public ReviewModelAssembler() {
        super(ProductController.class, ReviewMainPageDTO.class);
    }

    @Override
    public ReviewMainPageDTO toModel(ReviewMainPageDTO entity) {
        return entity.add(linkTo(methodOn(ProductController.class).getProductById(entity.getProductId(), null)).withRel("product"));
    }

    @Override
    public CollectionModel<ReviewMainPageDTO> toCollectionModel(Iterable<? extends ReviewMainPageDTO> entities) {
        return super.toCollectionModel(entities);
    }
}
