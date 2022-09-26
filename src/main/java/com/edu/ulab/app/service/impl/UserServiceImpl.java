package com.edu.ulab.app.service.impl;

import com.edu.ulab.app.dto.BookDto;
import com.edu.ulab.app.dto.UserDto;
import com.edu.ulab.app.entity.Person;
import com.edu.ulab.app.exception.NotFoundException;
import com.edu.ulab.app.mapper.UserMapper;
import com.edu.ulab.app.repository.UserRepository;
import com.edu.ulab.app.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository,
                           UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        Person user = userMapper.userDtoToPerson(userDto);
        log.info("Mapped user: {}", user);
        Person savedUser = userRepository.save(user);
        log.info("Saved user: {}", savedUser);
        return userMapper.personToUserDto(savedUser);
    }

    @Override
    public UserDto updateUser(UserDto userDto) {
        log.info("Update user: {}", userDto);
        Person person=userMapper.userDtoToPerson(userDto);
        userRepository.save(person);
        return userDto;
    }

    @Override
    public UserDto getUserById(Long id) {
        // реализовать недстающие методы
        log.info("Get user by id: {}", id);
        return userMapper.personToUserDto(userRepository.findById(id).orElseThrow(
                () -> new NotFoundException("cant find user with id: " + id)));
    }

    @Override
    public void deleteUserById(Long id) {
        log.info("Delete user by id: {}", id);
        userRepository.deleteById(id);
    }
}
