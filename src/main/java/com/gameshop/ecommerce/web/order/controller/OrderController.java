package com.gameshop.ecommerce.web.order.controller;

import com.gameshop.ecommerce.web.order.dto.WebOrderDto;
import com.gameshop.ecommerce.web.order.service.OrderService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/*
Please do not change this annotation to @RestController.
We need @Controller for redirect
*/
@Controller
@RequestMapping("/order")
public class OrderController {

    private final String frontendUrl;
    private final OrderService orderService;

    public OrderController(@Value("${app.frontend.url}") String frontendUrl,
                           OrderService orderService) {
        this.frontendUrl = frontendUrl;
        this.orderService = orderService;
    }

    @PostMapping
    public String createOrder(@RequestParam(name = "pid") List<UUID> productList,
                              @RequestParam(name = "q") List<Integer> quantityList,
                              @RequestParam(name = "address_id") UUID addressId,
                              @RequestParam(name = "user_id") UUID userId) {

        //todo: check referer header to make sure that request comes from the payment system

        final var order = orderService.addOrder(productList, quantityList, addressId, userId);

        return "redirect:" + frontendUrl + "/complete?order_id=" + order.getId();
    }

    @GetMapping(value = "/{orderId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebOrderDto> getOrder(@PathVariable UUID orderId) {
        final var dto = orderService
                .getOrder(orderId)
                .map(WebOrderDto::new);

        return ResponseEntity.of(dto);
    }
}
