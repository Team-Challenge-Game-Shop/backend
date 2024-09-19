package com.gameshop.ecommerce.web.order.dto;

import com.gameshop.ecommerce.web.address.dto.AddressDto;
import com.gameshop.ecommerce.web.address.mapper.AddressMapper;
import com.gameshop.ecommerce.web.order.model.WebOrder;
import com.gameshop.ecommerce.web.user.mapper.UserMapper;
import com.gameshop.ecommerce.web.user.model.UserInfoDTO;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
public class WebOrderDto {
    private String id;
    private List<WebOrderQuantityDto> quantities;
    private UserInfoDTO user;
    private AddressDto address;
    private Instant createdAt;
    private Integer total;

    public WebOrderDto(WebOrder webOrder) {
        this.id = webOrder.getId().toString();
        this.quantities = webOrder.getQuantities().stream()
                .map(WebOrderQuantityDto::new)
                .toList();
        this.user = UserMapper.INSTANCE.entityToUserInfoDto(webOrder.getUser());
        this.address = AddressMapper.INSTANCE.addressToAddressDto(webOrder.getAddress());
        this.createdAt = webOrder.getCreatedAt();

        this.total = webOrder.getQuantities().stream()
                .mapToInt(_q -> _q.getProduct().getPrice() * _q.getQuantity())
                .sum();
    }
}
