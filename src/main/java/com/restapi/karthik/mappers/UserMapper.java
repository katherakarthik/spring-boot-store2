package com.restapi.karthik.mappers;

import com.restapi.karthik.dtos.RegisterUserRequest;
import com.restapi.karthik.dtos.UpdateUserRequest;
import com.restapi.karthik.dtos.UserDTO;
import com.restapi.karthik.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")



public  interface     UserMapper {

    UserDTO toUserDTO(User user);
    User toEntity(RegisterUserRequest request);
    void updateUser(UpdateUserRequest request, @MappingTarget User user);

}
