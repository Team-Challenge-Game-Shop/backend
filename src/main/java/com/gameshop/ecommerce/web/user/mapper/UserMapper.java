package com.gameshop.ecommerce.web.user.mapper;

import com.gameshop.ecommerce.web.user.model.User;
import com.gameshop.ecommerce.web.user.model.UserDTO;
import com.gameshop.ecommerce.web.user.model.UserInfoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDTO entityToDto(User user);

    @Mapping(source = "phone", target = "phoneNumber")
    UserInfoDTO entityToUserInfoDto(User user);

    User dtoToEntity(UserDTO userDTO);
}
