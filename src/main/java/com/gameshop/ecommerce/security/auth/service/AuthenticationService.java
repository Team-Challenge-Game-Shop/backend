package com.gameshop.ecommerce.security.auth.service;

import com.gameshop.ecommerce.security.auth.model.GoogleTokenInfo;
import com.gameshop.ecommerce.security.auth.model.LoginResponse;
import com.gameshop.ecommerce.security.auth.model.RegistrationRequest;
import com.gameshop.ecommerce.utils.exception.JwtTokenException;
import com.gameshop.ecommerce.web.user.model.User;
import com.gameshop.ecommerce.web.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final RestTemplate restTemplate;

    public LoginResponse authenticateGoogle(String googleToken) {
        GoogleTokenInfo tokenInfo = verifyGoogleToken(googleToken);
        log.info("Google token info: {}", tokenInfo);
        User user = (User) userService.userDetailsService().loadUserByUsername(tokenInfo.getEmail());
        // create user if not exists
        if (user == null) {
            user = userService.create(User.builder()
                    .firstName(tokenInfo.getGivenName())
                    .lastName(tokenInfo.getFamilyName())
                    .email(tokenInfo.getEmail())
                    .isEmailVerified(true)
                    .password(passwordEncoder.encode(googleToken))
                    .phone("")
                    .userPhoto(tokenInfo.getPicture())
                    .build());
        }
        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        return new LoginResponse(accessToken, refreshToken);
    }

    private GoogleTokenInfo verifyGoogleToken(String token) {
        final String url = "https://oauth2.googleapis.com/tokeninfo?id_token=" + token;
        log.info("Verifying Google token at: {}", url);
        return restTemplate.getForObject(url, GoogleTokenInfo.class);
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
