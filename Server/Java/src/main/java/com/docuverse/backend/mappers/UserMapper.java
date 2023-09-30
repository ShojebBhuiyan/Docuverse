package com.docuverse.backend.mappers;

import com.docuverse.backend.dtos.SignUpDTO;
import com.docuverse.backend.dtos.UserDTO;
import com.docuverse.backend.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO toUserDto(User user);

    @Mapping(target = "password", ignore = true)
    User signUpToUser(SignUpDTO signUpDto);

}
