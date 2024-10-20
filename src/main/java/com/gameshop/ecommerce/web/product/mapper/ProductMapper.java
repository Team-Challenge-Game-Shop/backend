package com.gameshop.ecommerce.web.product.mapper;

import com.gameshop.ecommerce.web.product.dto.ProductCatalogDTO;
import com.gameshop.ecommerce.web.product.model.Brand;
import com.gameshop.ecommerce.web.product.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    @Mapping(source = "averageRate", target = "rating")
    ProductCatalogDTO productToProductCatalogDTO(Product product);

    List<ProductCatalogDTO> productsToProductCatalogDTOs(List<Product> products);

    default String map(Brand brand) {
        return brand == null ? null : brand.getName();
    }
}
