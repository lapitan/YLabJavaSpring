package com.edu.ulab.app.storage;

import com.edu.ulab.app.dto.BookDto;
import com.edu.ulab.app.dto.UserDto;
import com.edu.ulab.app.entity.UserEntity;
import com.edu.ulab.app.exception.NotFoundException;
import com.edu.ulab.app.mapper.UserMapper;

import java.util.HashMap;
import java.util.List;

public class UserStorage {

    private HashMap<Long, UserEntity> userEntities;
    private Long currUserId;

    public UserStorage() {
        currUserId=0L;
        userEntities=new HashMap<>();
    }

    public UserDto createUser(UserDto userDto) {
        userDto.setId(currUserId);
        currUserId++;
        UserEntity userEntity = new UserEntity(userDto);
        userEntities.put(userEntity.getId(), userEntity);
        userDto = userEntity.makeUserDto();

        return userDto;
    }

    public UserDto getUserById(Long id) {
        return userEntities.get(id).makeUserDto();
    }

    public List<Long> getAllUserBooks(Long id) {
        return userEntities.get(id).getBooksId();
    }

    public void deleteUser(Long id) {
        userEntities.remove(id);
    }

    public UserDto updateUser(UserEntity userEntity) {

        UserEntity currUserEntity=userEntities.get(userEntity.getId());
        currUserEntity= userEntity;
        return userEntities.get(currUserEntity.getId()).makeUserDto();
    }

    public void deleteBookFromUser(BookDto bookDto) {
        userEntities.get(bookDto.getUserId()).removeConcreteBook(bookDto.getId());
    }

    public void addBookToUser(BookDto bookDto){
        userEntities.get(bookDto.getUserId()).putBookId(bookDto.getId());
    }
}
