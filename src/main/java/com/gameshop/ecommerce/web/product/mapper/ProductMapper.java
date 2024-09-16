package com.gameshop.ecommerce.web.product.mapper;

import com.gameshop.ecommerce.web.product.dto.ProductCatalogDTO;
import com.gameshop.ecommerce.web.product.model.Brand;
import com.gameshop.ecommerce.web.product.model.Product;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductCatalogDTO productToProductCatalogDTO(Product product);

    List<ProductCatalogDTO> productsToProductCatalogDTOs(List<Product> products);

    default String map(Brand brand) {
        return brand == null ? null : brand.getName();
    }
}
