package com.gameshop.ecommerce.web.address;

import com.gameshop.ecommerce.web.address.dto.AddressDto;
import com.gameshop.ecommerce.web.address.service.AddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@Slf4j
@RequestMapping("/addresses")
@RequiredArgsConstructor
@Tag(name = "Address", description = "API to work with Address")
public class AddressController {
    private final AddressService addressService;

    @Operation(summary = "Create a new address")
    @PostMapping("/{userId}")
    public ResponseEntity<Address> createAddress(@Valid @PathVariable UUID userId, @RequestBody AddressDto addressDto) {
        log.info("Creating new address: {}", addressDto);
        return ResponseEntity.ok(addressService.createAddress(addressDto, userId));
    }

    @Operation(summary = "Get an address by ID")
    @GetMapping("/{userId}/{addressId}")
    public ResponseEntity<AddressDto> getAddress(@PathVariable UUID userId, @PathVariable UUID addressId) {
        AddressDto addressDto = addressService.getAddress(userId, addressId);
        return ResponseEntity.ok(addressDto);
    }

    @Operation(summary = "Get all addresses")
    @GetMapping("/{userId}")
    public ResponseEntity<List<AddressDto>> getAllAddresses(@PathVariable UUID userId) {
        List<AddressDto> addresses = addressService.getAllAddresses(userId);
        return ResponseEntity.ok(addresses);
    }

    @Operation(summary = "Update an address by ID")
    @PutMapping("/{userId}/{addressId}")
    public ResponseEntity<AddressDto> updateAddress(@PathVariable UUID userId, @PathVariable UUID addressId, @RequestBody AddressDto addressDto) {
        AddressDto updatedAddress = addressService.updateAddress(userId, addressId, addressDto);
        return ResponseEntity.ok(updatedAddress);
    }

    @Operation(summary = "Delete an address by ID")
    @DeleteMapping("/{addressId}")
    public ResponseEntity<Void> deleteAddress(@PathVariable UUID addressId) {
        addressService.deleteAddress(addressId);
        return ResponseEntity.noContent().build();
    }
}
