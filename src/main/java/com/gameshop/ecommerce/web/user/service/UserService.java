package com.gameshop.ecommerce.web.user.service;

import com.gameshop.ecommerce.utils.exception.EmailAlreadyExistsException;
import com.gameshop.ecommerce.web.user.model.User;
import com.gameshop.ecommerce.web.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User getByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User with email " + email + " not found"));
    }

    public UserDetailsService userDetailsService() {
        return this::getByEmail;
    }

    public User create(User user) {
        if (Boolean.TRUE.equals(userRepository.existsByEmail(user.getEmail()))) {
            throw new EmailAlreadyExistsException("User with email " + user.getEmail() + " has already exists");
        }
        return userRepository.save(user);
    }

    public boolean resetPassword(String passwordResetCode, String password) {
        User user = getByPasswordResetCode(passwordResetCode);
        if (user.getPasswordResetCode() == null || user.getPasswordResetCode().isBlank()) {
            return false;
        }
        user.setPassword(passwordEncoder.encode(password));
        user.setPasswordResetCode(null);
        userRepository.save(user);
        return true;
    }

    public User getByPasswordResetCode(String passwordResetCode) {
        return userRepository.findByPasswordResetCode(passwordResetCode)
                .orElseThrow(() -> new EntityNotFoundException("User not found with password reset code: " + passwordResetCode));
    }

    public User update(UUID id, User user) {
        user.setId(id);
        return userRepository.save(user);
    }
}