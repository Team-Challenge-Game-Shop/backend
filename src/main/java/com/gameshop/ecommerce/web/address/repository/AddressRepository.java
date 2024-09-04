package com.gameshop.ecommerce.web.address.repository;

import com.gameshop.ecommerce.web.address.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AddressRepository extends JpaRepository<Address, UUID> {
    Optional<Address> findById(UUID id);

    void deleteById(UUID id);
}
