package com.edu.ulab.app.mapper;

import com.edu.ulab.app.dto.UserDto;
import com.edu.ulab.app.entity.Person;
import com.edu.ulab.app.web.request.UserRequest;
import com.edu.ulab.app.web.request.UserRequestWithId;
import org.mapstruct.Mapper;
import org.springframework.jdbc.core.RowMapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto userRequestToUserDto(UserRequest userRequest);

    UserRequest userDtoToUserRequest(UserDto userDto);

    Person userDtoToPerson(UserDto userDto);

    UserDto personToUserDto(Person person);

    UserDto userRequestWithIdToUserDto(UserRequestWithId userRequestWithId);
}
