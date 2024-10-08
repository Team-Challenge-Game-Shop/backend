package com.gameshop.ecommerce.web.generator.controller;

import com.gameshop.ecommerce.web.generator.service.OrderGeneratorService;
import com.gameshop.ecommerce.web.generator.service.ProductGeneratorService;
import com.gameshop.ecommerce.web.generator.service.UserGeneratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/generate")
public class GeneratorController {

    private final ProductGeneratorService productGeneratorService;
    private final OrderGeneratorService orderGeneratorService;
    private final UserGeneratorService userGeneratorService;

    @CrossOrigin
    @PostMapping("/products")
    public ResponseEntity<Void> generateProducts(
            @RequestParam(required = false, defaultValue = "1000") int amount
    ) {
        productGeneratorService.generateProducts(amount);
        return ResponseEntity.ok().build();
    }

    @CrossOrigin
    @PostMapping("/orders")
    public ResponseEntity<Void> generateOrders() {
        orderGeneratorService.generateOrders();
        return ResponseEntity.ok().build();
    }

    @CrossOrigin
    @PostMapping("/users")
    public ResponseEntity<Void> generateUsers() {
        userGeneratorService.generateUsers();
        return ResponseEntity.ok().build();
    }
}
