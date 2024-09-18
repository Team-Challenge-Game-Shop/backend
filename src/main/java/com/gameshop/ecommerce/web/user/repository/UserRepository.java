package com.gameshop.ecommerce.web.user.repository;

import com.gameshop.ecommerce.web.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmailIgnoreCase(String email);

    Boolean existsByEmail(String email);

    Optional<User> findByPhone(String phone);

    Optional<User> findByEmail(String email);

    Optional<User> findByPasswordResetCode(String passwordResetCode);

    @Query("SELECT u FROM User u WHERE u.confirmationCode = :confirmationCode")
    Optional<User> findByConfirmationCode(String confirmationCode);
}