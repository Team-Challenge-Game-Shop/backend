package com.gameshop.ecommerce.web.user.service;

import com.gameshop.ecommerce.web.user.mapper.UserMapper;
import com.gameshop.ecommerce.web.user.model.User;
import com.gameshop.ecommerce.web.user.model.UserDTO;
import com.gameshop.ecommerce.web.user.model.UserInfoDTO;
import com.gameshop.ecommerce.web.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;


@Service
@Slf4j
@RequiredArgsConstructor
public class AccountService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public Optional<UserInfoDTO> getUser(User user) {
        return Optional.ofNullable(userMapper.entityToUserInfoDto(user));
    }

    public UserDTO updateInfo(User user, UserDTO userDto) {
        if ((userDto.getNewPassword() != null && !userDto.getNewPassword().isEmpty())) {
            updatePassword(user, userDto);
        } else {
            log.error("New password cannot be empty");
            throw new IllegalArgumentException("New password cannot be empty");
        }
        updateUserFields(user, userDto);

        return userMapper.entityToDto(userRepository.save(user));
    }

    private void updateUserFields(User user, UserDTO userDto) {
        Map<Consumer<String>, String> updates = new HashMap<>();
        updates.put(user::setFirstName, userDto.getFirstName());
        updates.put(user::setLastName, userDto.getLastName());
        updates.put(user::setEmail, userDto.getEmail());
        updates.put(user::setPhone, userDto.getPhoneNumber());

        updates.forEach((setter, value) -> {
            if (value != null && !value.isEmpty()) {
                setter.accept(value);
            }
        });
    }

    private void updatePassword(User user, UserDTO userDto) {
        if (passwordEncoder.matches(userDto.getOldPassword(), user.getPassword())) {
            user.setPassword(passwordEncoder.encode(userDto.getNewPassword()));
        } else {
            log.error("Wrong old password");
            throw new IllegalArgumentException("Wrong old password");
        }
    }

}
