package com.edu.ulab.app.mapper;

import com.edu.ulab.app.dto.UserDto;
import com.edu.ulab.app.entity.UserEntity;
import com.edu.ulab.app.web.request.UserRequest;
import com.edu.ulab.app.web.request.UserRequestWithId;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto userRequestToUserDto(UserRequest userRequest);

    UserRequest userDtoToUserRequest(UserDto userDto);

    UserDto userRequestWithIdToUserDto(UserRequestWithId userRequestWithId);

    UserEntity userDtoToUserEntity(UserDto userDto);
}
