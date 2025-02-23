package com.example.demo.application.mappers;

import com.example.demo.application.dtos.UserResponseDTO;
import com.example.demo.domain.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper( UserMapper.class );

    UserResponseDTO userToUserResponseDTO(User user);

    User userResponseDTOToUser(UserResponseDTO userDTO);

}
