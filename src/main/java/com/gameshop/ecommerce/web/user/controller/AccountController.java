package com.gameshop.ecommerce.web.user.controller;

import com.gameshop.ecommerce.utils.MessageResponse;
import com.gameshop.ecommerce.web.user.model.User;
import com.gameshop.ecommerce.web.user.model.UserDTO;
import com.gameshop.ecommerce.web.user.model.UserInfoDTO;
import com.gameshop.ecommerce.web.user.service.AccountService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
@Tag(name = "MyAccount", description = "API to work with My Account page")
public class AccountController {

    private final AccountService accountService;

    @GetMapping
    public ResponseEntity<UserInfoDTO> getUserInfo(@AuthenticationPrincipal User user) {
        final var userDto = accountService.getUser(user);
        return ResponseEntity.of(userDto);
    }

    @PutMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<?> updateInfo(@AuthenticationPrincipal User user, @Valid @ModelAttribute UserDTO userDto) {
        try {
            final var updatedUser = accountService.updateInfo(user, userDto);
            return ResponseEntity.ok().body(updatedUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }
}