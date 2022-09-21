package com.edu.ulab.app.service;

import com.edu.ulab.app.dto.BookDto;
import com.edu.ulab.app.dto.UserDto;
import com.edu.ulab.app.entity.UserEntity;

public interface UserService {
    UserDto createUser(UserDto userDto);

    UserDto updateUser(UserEntity userDto);

    UserDto getUserById(Long id);

    void deleteUserById(Long id);

    void addBookToUser(BookDto bookDto);
}
