package com.gameshop.ecommerce.web.order.dto;

import com.gameshop.ecommerce.web.order.model.WebOrderQuantity;
import com.gameshop.ecommerce.web.product.dto.ProductCatalogDTO;
import com.gameshop.ecommerce.web.product.mapper.ProductMapper;
import lombok.Data;

@Data
public class WebOrderQuantityDto {
    private String id;
    private Integer quantity;
    private ProductCatalogDTO product;

    public WebOrderQuantityDto(WebOrderQuantity value) {
        this.id = value.getId().toString();
        this.quantity = value.getQuantity();
        product = ProductMapper.INSTANCE.productToProductCatalogDTO(value.getProduct());
    }
}
