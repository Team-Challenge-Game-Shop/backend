package com.gameshop.ecommerce.web.user.model;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    @Size(max = 100)
    private String firstName;

    @Size(max = 100)
    private String lastName;

    @Size(max = 320)
    private String email;

    @Size(max = 25)
    private String phoneNumber;

    @Size(max = 1000)
    private String oldPassword;

    @Size(max = 1000)
    private String newPassword;
}
