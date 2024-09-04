package com.gameshop.ecommerce.web.address.service;

import com.gameshop.ecommerce.utils.exception.AddressNotFoundException;
import com.gameshop.ecommerce.web.address.Address;
import com.gameshop.ecommerce.web.address.dto.AddressDto;
import com.gameshop.ecommerce.web.address.mapper.AddressMapper;
import com.gameshop.ecommerce.web.address.repository.AddressRepository;
import com.gameshop.ecommerce.web.user.model.User;
import com.gameshop.ecommerce.web.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;
    private final UserRepository userRepository;

    @Override
    public Address createAddress(AddressDto addressDto, UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Address address = new Address();
        address.setFirstName(addressDto.getFirstName());
        address.setLastName(addressDto.getLastName());
        address.setContactNumber(addressDto.getContactNumber());
        address.setCountry(addressDto.getCountry());
        address.setCity(addressDto.getCity());
        address.setAddressLine(addressDto.getAddressLine());
        address.setPostcode(addressDto.getPostcode());
        address.setUser(user);

        return addressRepository.save(address);
    }

    @Override
    public AddressDto getAddress(UUID userId, UUID addressId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new AddressNotFoundException("Address not found"));
        if (address.getUser().getId() == userId) {
            return addressBuilder(address);
        } else {
            throw new AddressNotFoundException("Address not found");
        }
    }

    @Override
    public List<AddressDto> getAllAddresses(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return addressMapper.addressesToAddressDtos(user.getAddresses());
    }

    @Override
    public AddressDto updateAddress(UUID userId, UUID addressId, AddressDto addressDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new AddressNotFoundException("Address not found"));

        if (address.getUser().getId() == userId) {
            address.setFirstName(addressDto.getFirstName());
            address.setLastName(addressDto.getLastName());
            address.setContactNumber(addressDto.getContactNumber());
            address.setCountry(addressDto.getCountry());
            address.setCity(addressDto.getCity());
            address.setAddressLine(addressDto.getAddressLine());
            address.setPostcode(addressDto.getPostcode());
        }

        Address updatedAddress = addressRepository.save(address);
        return addressMapper.addressToAddressDto(updatedAddress);
    }

    @Override
    public void deleteAddress(UUID addressId) {
        addressRepository.deleteById(addressId);
    }

    public AddressDto addressBuilder(Address address) {
        return AddressDto.builder()
                .firstName(address.getFirstName())
                .lastName(address.getLastName())
                .contactNumber(address.getContactNumber())
                .country(address.getCountry())
                .city(address.getCity())
                .addressLine(address.getAddressLine())
                .postcode(address.getPostcode())
                .build();
    }
}
