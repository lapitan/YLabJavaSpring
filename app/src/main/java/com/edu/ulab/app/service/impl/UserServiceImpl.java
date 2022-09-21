package com.edu.ulab.app.service.impl;

import com.edu.ulab.app.dto.BookDto;
import com.edu.ulab.app.dto.UserDto;
import com.edu.ulab.app.entity.UserEntity;
import com.edu.ulab.app.service.UserService;
import com.edu.ulab.app.storage.Storage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    Storage storage;

    public UserServiceImpl() {
        storage = Storage.createStorage();
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        // сгенерировать идентификатор
        // создать пользователя
        // вернуть сохраненного пользователя со всеми необходимыми полями id
        userDto = storage.createUser(userDto);
        log.info("create user with id: {}",userDto.getId());
        return userDto;
    }

    @Override
    public UserDto updateUser(UserEntity userEntity) {
        try {
            UserDto userDto=storage.updateUser(userEntity);
            log.info("create user with id: {}",userDto.getId());
            return userDto;
        } catch (NullPointerException exception) {
            throw new NullPointerException("cant find user with id: " + userEntity.getId().toString());
        }
    }

    @Override
    public UserDto getUserById(Long id) {
        try {
            UserDto userDto=storage.getUserById(id);
            log.info("get user with id: {}",userDto.getId());
            return userDto;
        } catch (NullPointerException exception) {
            throw new NullPointerException("cant find user with id: " + id.toString());
        }
    }

    @Override
    public void deleteUserById(Long id) {
        try {
            storage.deleteUser(id);
            log.info("delete user with id: {}",id);
        } catch (NullPointerException exception) {
            throw new NullPointerException("cant find user with id: " + id.toString());
        }
    }

    @Override
    public void addBookToUser(BookDto bookDto) {
        try {
            storage.addBookToUser(bookDto);
            log.info("add book with id: {} to user with id: {}",bookDto.getId(),bookDto.getUserId());
        } catch (NullPointerException exception) {
            throw new NullPointerException("cant find user with id: " + bookDto.getUserId().toString());
        }
    }
}
