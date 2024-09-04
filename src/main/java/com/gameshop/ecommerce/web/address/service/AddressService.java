package com.gameshop.ecommerce.web.address.service;

import com.gameshop.ecommerce.utils.exception.AddressNotFoundException;
import com.gameshop.ecommerce.web.address.Address;
import com.gameshop.ecommerce.web.address.dto.AddressDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface AddressService {
    Address createAddress(AddressDto addressDto, UUID userId);

    AddressDto getAddress(UUID userId, UUID addressId) throws AddressNotFoundException;

    List<AddressDto> getAllAddresses(UUID userId);

    AddressDto updateAddress(UUID userId, UUID addressID, AddressDto addressDto);

    void deleteAddress(UUID addressId);

}
