package com.gameshop.ecommerce.web.user.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserInfoDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
}
