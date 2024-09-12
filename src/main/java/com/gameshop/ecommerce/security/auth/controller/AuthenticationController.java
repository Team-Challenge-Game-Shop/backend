package com.gameshop.ecommerce.security.auth.controller;

import com.gameshop.ecommerce.security.auth.model.*;
import com.gameshop.ecommerce.security.auth.service.AuthenticationService;
import com.gameshop.ecommerce.utils.MessageResponse;
import com.gameshop.ecommerce.utils.exception.EmailAlreadyExistsException;
import com.gameshop.ecommerce.web.user.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    private final ApplicationEventPublisher eventPublisher;

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public LoginResponse loginUser(@ModelAttribute LoginRequest loginRequest) {
        return authenticationService.login(loginRequest.getEmail(), loginRequest.getPassword());
    }

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public MessageResponse registerUser(@ModelAttribute RegistrationRequest registrationRequest) {
        User registered = authenticationService.register(registrationRequest);
        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(registered));
        return new MessageResponse("User registered");
    }

    @PostMapping(value = "/refresh")
    public ResponseEntity<LoginResponse> refreshToken(@RequestHeader(value = "Authorization") String authorizationHeader) {
        String refreshToken = getToken(authorizationHeader);
        LoginResponse response = authenticationService.refreshToken(refreshToken);
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/google", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LoginResponse> authenticateGoogle(@RequestBody TokenRequest tokenRequest) {
        LoginResponse response = authenticationService.authenticateGoogle(tokenRequest.getToken());
        return ResponseEntity.ok(response);
    }

    private static String getToken(String authorizationHeader) {
        return authorizationHeader.substring("Bearer ".length());
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<MessageResponse> handleEmailAlreadyExistsException(EmailAlreadyExistsException ex) {
        return new ResponseEntity<>(new MessageResponse(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
