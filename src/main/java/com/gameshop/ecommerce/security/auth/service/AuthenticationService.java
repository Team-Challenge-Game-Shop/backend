package com.gameshop.ecommerce.security.auth.service;

import com.gameshop.ecommerce.security.auth.model.LoginResponse;
import com.gameshop.ecommerce.security.auth.model.RegistrationRequest;
import com.gameshop.ecommerce.utils.exception.JwtTokenException;
import com.gameshop.ecommerce.web.user.model.User;
import com.gameshop.ecommerce.web.user.service.UserService;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String googleClientId;

    public LoginResponse authenticateGoogle(String googleToken) {
        Optional<User> verifiedUser = getUserFromGoogleIdToken(googleToken);
        if (verifiedUser.isEmpty()) {
            throw new JwtTokenException("Invalid Google token");
        }
        String accessToken = jwtService.generateAccessToken(verifiedUser.get());
        String refreshToken = jwtService.generateRefreshToken(verifiedUser.get());
        return new LoginResponse(accessToken, refreshToken);
    }

    private Optional<User> getUserFromGoogleIdToken(String token) {
        try {
            final var googleIdToken = new GoogleIdTokenVerifier.Builder(
                    new NetHttpTransport(), new GsonFactory())
                    .setAudience(List.of(googleClientId))
                    .build()
                    .verify(token);

            if (googleIdToken != null) {
                final var payload = googleIdToken.getPayload();
                // check if user with this email already exists
                User user = (User) userService.userDetailsService().loadUserByUsername(payload.getEmail());
                if (user != null) {
                    return Optional.of(user);
                }
                final var firstName = (String) payload.get("given_name");
                final var lastName = (String) payload.get("family_name");
                final var emailVerified = (Boolean) payload.get("email_verified");
                final var pictureUrl = (String) payload.get("picture");
                final var email = payload.getEmail();

                return Optional.of(User.builder()
                        .firstName(firstName)
                        .lastName(lastName)
                        .password(passwordEncoder.encode(token))
                        .phone("")
                        .email(email)
                        .isEmailVerified(emailVerified)
                        .userPhoto(pictureUrl)
                        .build());
            }

            return Optional.empty();
        } catch (GeneralSecurityException | IOException e) {
            log.error("Failed to verify google token: ", e);
            return Optional.empty();
        }
    }

    public LoginResponse login(String email, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                email,
                password
        ));

        User user = (User) userService.userDetailsService().loadUserByUsername(email);

        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        return new LoginResponse(accessToken, refreshToken);
    }

    public User register(RegistrationRequest request) {
        return userService.create(User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .phone(request.getPhone())
                .isEmailVerified(true)
                .build());
    }

    public LoginResponse refreshToken(String refreshToken) {
        if (!jwtService.isRefreshToken(refreshToken))
            throw new JwtTokenException("Only refresh tokens are allowed");

        String email = jwtService.extractEmail(refreshToken);
        User user = (User) userService.userDetailsService().loadUserByUsername(email);

        String newAccessToken = jwtService.generateAccessToken(user);
        String newRefreshToken = jwtService.generateRefreshToken(user);
        return new LoginResponse(newAccessToken, newRefreshToken);
    }
}
