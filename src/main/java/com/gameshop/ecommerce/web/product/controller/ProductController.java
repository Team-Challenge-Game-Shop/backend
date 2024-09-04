package com.gameshop.ecommerce.web.product.controller;


import com.gameshop.ecommerce.web.product.dto.ProductCatalogDTO;
import com.gameshop.ecommerce.web.product.dto.ProductDetailDTO;
import com.gameshop.ecommerce.web.product.dto.ProductModelAssembler;
import com.gameshop.ecommerce.web.product.filter.FilterService;
import com.gameshop.ecommerce.web.product.filter.ProductFilterDTO;
import com.gameshop.ecommerce.web.product.model.Product;
import com.gameshop.ecommerce.web.product.service.ProductMapperService;
import com.gameshop.ecommerce.web.product.service.ProductService;
import com.gameshop.ecommerce.web.user.model.User;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final ProductMapperService productMapperService;
    private final ProductModelAssembler productModelAssembler;
    private final PagedResourcesAssembler<ProductCatalogDTO> pagedResourcesAssembler;
    private final FilterService filterService;

    @CrossOrigin
    @GetMapping()
    public ResponseEntity<PagedModel<ProductCatalogDTO>> getProducts(@QuerydslPredicate(root = Product.class) Predicate predicate,
                                                                     @RequestParam(required = false) Map<String, String> allRequestParams,
                                                                     Pageable pageable, @AuthenticationPrincipal User user) {

        Page<ProductCatalogDTO> products = productService.getProducts(predicate, pageable, allRequestParams)
                .map(product -> productMapperService.toModel(product, user));
        PagedModel<ProductCatalogDTO> pagedModel = pagedResourcesAssembler.toModel(products, productModelAssembler);
        return new ResponseEntity<>(pagedModel, HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<ProductDetailDTO>> getProductById(@PathVariable UUID id,
                                                                        @AuthenticationPrincipal User user) {

        return productService.getProductById(id)
                .map(product -> productMapperService.toModelDetail(product, user))
                .map(productModelAssembler::toModelDetail)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @CrossOrigin
    @GetMapping("/filters")
    public ResponseEntity<ProductFilterDTO> getFilters(@QuerydslPredicate(root = Product.class) Predicate predicate) {
        ProductFilterDTO productFilterDTO = filterService.getFilters(predicate);
        return ResponseEntity.ok(productFilterDTO);
    }


    @CrossOrigin
    @GetMapping("/most-purchase")
    public ResponseEntity<CollectionModel<ProductCatalogDTO>> getMostPurchasedProducts(@AuthenticationPrincipal User user) {
        List<ProductCatalogDTO> products = productService.getMostPurchasedProducts()
                .stream()
                .map(product -> productMapperService.toModel(product, user))
                .toList();

        CollectionModel<ProductCatalogDTO> collectionModel = productModelAssembler.toCollectionModel(products);
        return ResponseEntity.ok(collectionModel);
    }

    @CrossOrigin
    @GetMapping("/by-ids")
    public ResponseEntity<CollectionModel<ProductCatalogDTO>> getProductsByIds(@RequestParam List<UUID> ids,
                                                                               @AuthenticationPrincipal User user) {

        List<ProductCatalogDTO> products = productService.getProductsByIds(ids)
                .stream()
                .map(p -> productMapperService.toModel(p, user))
                .toList();

        CollectionModel<ProductCatalogDTO> collectionModel = productModelAssembler.toCollectionModel(products);
        return ResponseEntity.ok(collectionModel);
    }
}
