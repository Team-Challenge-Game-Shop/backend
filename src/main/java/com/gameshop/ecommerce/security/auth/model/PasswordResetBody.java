package com.gameshop.ecommerce.security.auth.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PasswordResetBody {
    @NotBlank
    @NotNull
    private String token;
    @NotNull
    @NotBlank
    private String password;

}
