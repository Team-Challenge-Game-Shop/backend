package com.gameshop.ecommerce.web.product.filter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductFilterDTO {
    Set<String> brands;
    Map<String, Integer> price;
    Map<String, Set<String>> characteristics;
    Set<Boolean> isPresent;
    Set<Boolean> isSale;
}