package com.edu.ulab.app.storage;

import com.edu.ulab.app.dto.BookDto;
import com.edu.ulab.app.dto.UserDto;
import com.edu.ulab.app.entity.BookEntity;
import com.edu.ulab.app.entity.UserEntity;
import com.edu.ulab.app.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;

import java.awt.print.Book;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
public class Storage {
    //todo создать хранилище в котором будут содержаться данные
    // сделать абстракции через которые можно будет производить операции с хранилищем
    // продумать логику поиска и сохранения
    // продумать возможные ошибки
    // учесть, что при сохранеии юзера или книги, должен генерироваться идентификатор
    // продумать что у узера может быть много книг и нужно создать эту связь
    // так же учесть, что методы хранилища принимают друго тип данных - учесть это в абстракции
    private static Storage instance;
    private HashMap<Long, BookEntity> bookEntities;
    private Long currBookId;
    private HashMap<Long, UserEntity> userEntities;
    private Long currUserId;

    public static Storage createStorage() {
        if (instance == null) {
            instance = new Storage();
        }
        return instance;
    }

    private Storage() {
        bookEntities = new HashMap<>();
        userEntities = new HashMap<>();
        currUserId = 0L;
        currBookId = 0L;
    }

    public UserDto createUser(UserDto userDto) {
        userDto.setId(currUserId);
        currUserId++;
        UserEntity userEntity = new UserEntity(userDto);
        userEntities.put(userEntity.getId(), userEntity);
        userDto = userEntity.makeUserDto();

        log.info("created new user with id:{}", userEntity.getId());

        return userDto;
    }

    public BookDto createBook(BookDto bookDto) {
        bookDto.setId(currBookId);
        currBookId++;
        userEntities.get(bookDto.getUserId()).putBookId(bookDto.getId());
        BookEntity bookEntity = new BookEntity(bookDto);
        bookEntities.put(bookEntity.getId(), bookEntity);
        bookDto = bookEntity.makeBookDto();

        log.info("created new book with id:{}", bookEntity.getId());

        return bookDto;
    }

    public UserDto getUserById(Long id) {
        if (!userEntities.containsKey(id)){
            throw new NotFoundException("cant find user with id: "+id.toString());
        }
        return userEntities.get(id).makeUserDto();
    }

    public List<Long> getAllUserBooks(Long id) {
        if (!userEntities.containsKey(id)){
            throw new NotFoundException("cant find user with id: "+id.toString());
        }
        return userEntities.get(id).getBooksId();
    }

    public BookDto getBookById(Long id) {
        if (!bookEntities.containsKey(id)){
            throw new NotFoundException("cant find book with id: "+id.toString());
        }
        return bookEntities.get(id).makeBookDto();
    }

    public void deleteUser(Long id) {
        if (!userEntities.containsKey(id)){
            throw new NotFoundException("cant find user with id: "+id.toString());
        }
        userEntities.remove(id);
    }

    public void deleteBook(Long id) {
        if (!bookEntities.containsKey(id)){
            throw new NotFoundException("cant find book with id: "+id.toString());
        }
        bookEntities.remove(id);
    }

    public UserDto updateUser(UserDto userDto) {
        if (!bookEntities.containsKey(userDto.getId())){
            throw new NotFoundException("cant find user with id"+userDto.getId());
        }
        userEntities.get(userDto.getId()).setAge(userDto.getAge());
        userEntities.get(userDto.getId()).setName(userDto.getFullName());
        userEntities.get(userDto.getId()).setTitle(userDto.getTitle());

        return userEntities.get(userDto.getId()).makeUserDto();
    }

    public void deleteBookFromUser(BookDto bookDto){
        if (!bookEntities.containsKey(bookDto.getUserId())){
            throw new NotFoundException("cant find user with id"+bookDto.getUserId());
        }
        userEntities.get(bookDto.getUserId()).removeConcreteBook(bookDto.getId());
    }
}
