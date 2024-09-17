package com.gameshop.ecommerce.web.user.service;

import com.gameshop.ecommerce.web.user.model.User;
import com.gameshop.ecommerce.web.user.model.UserDTO;
import com.gameshop.ecommerce.web.user.model.UserInfoDTO;

import java.util.Optional;

public interface AccountService {
    Optional<UserInfoDTO> getUser(User user);

    UserInfoDTO updateInfo(User user, UserDTO userDto);
}
