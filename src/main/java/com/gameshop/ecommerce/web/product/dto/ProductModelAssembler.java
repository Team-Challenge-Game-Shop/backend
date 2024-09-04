package com.gameshop.ecommerce.web.product.dto;

import com.gameshop.ecommerce.web.product.controller.ProductController;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ProductModelAssembler extends RepresentationModelAssemblerSupport<ProductCatalogDTO, ProductCatalogDTO> {

    public ProductModelAssembler() {
        super(ProductController.class, ProductCatalogDTO.class);
    }

    @Override
    public ProductCatalogDTO toModel(ProductCatalogDTO entity) {
        return entity.add(linkTo(methodOn(ProductController.class).getProductById(entity.getId(), null)).withSelfRel());
    }

    public EntityModel<ProductDetailDTO> toModelDetail(ProductDetailDTO entity) {
        return EntityModel.of(entity.add(linkTo(methodOn(ProductController.class).getProductById(entity.getId(), null)).withSelfRel(),
                linkTo(methodOn(ProductController.class)
                        .getProducts(null, null, null, null))
                        .withRel("products")));
    }

    @Override
    public CollectionModel<ProductCatalogDTO> toCollectionModel(Iterable<? extends ProductCatalogDTO> entities) {
        return super.toCollectionModel(entities)
                .add(linkTo(methodOn(ProductController.class)
                        .getProducts(null, null, null, null)).withSelfRel());
    }

}
