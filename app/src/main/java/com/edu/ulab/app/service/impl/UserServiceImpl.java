package com.edu.ulab.app.service.impl;

import com.edu.ulab.app.dto.UserDto;
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
        storage=Storage.createStorage();
    }
    @Override
    public UserDto createUser(UserDto userDto) {
        // сгенерировать идентификатор
        // создать пользователя
        // вернуть сохраненного пользователя со всеми необходимыми полями id
        return storage.createUser(userDto);
    }

    @Override
    public UserDto updateUser(UserDto userDto) {
        return storage.updateUser(userDto);
    }

    @Override
    public UserDto getUserById(Long id) {
        return storage.getUserById(id);
    }

    @Override
    public void deleteUserById(Long id) {
        storage.deleteUser(id);
    }
}
