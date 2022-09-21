package com.edu.ulab.app.storage;

import com.edu.ulab.app.dto.BookDto;
import com.edu.ulab.app.dto.UserDto;
import com.edu.ulab.app.entity.BookEntity;
import com.edu.ulab.app.entity.UserEntity;
import com.edu.ulab.app.exception.CantAccessException;
import com.edu.ulab.app.exception.DataBaseException;
import com.edu.ulab.app.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;

import java.awt.print.Book;
import java.rmi.AccessException;
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

    private BookStorage bookStorage;

    private UserStorage userStorage;

    public static Storage createStorage() {
        if (instance == null) {
            instance = new Storage();
        }
        return instance;
    }

    private Storage() {
        bookStorage = new BookStorage();
        userStorage = new UserStorage();
    }

    public UserDto createUser(UserDto userDto) {

        workCheck();
        userDto = userStorage.createUser(userDto);
        log.info("created new user with id:{}", userDto.getId());
        return userDto;
    }

    public BookDto createBook(BookDto bookDto) {
        workCheck();
        bookDto=bookStorage.createBook(bookDto);
        log.info("created new book with id:{}", bookDto.getId());

        return bookDto;
    }

    public UserDto getUserById(Long id) {
        workCheck();
        return userStorage.getUserById(id);
    }

    public List<Long> getAllUserBooks(Long id) {
        workCheck();
        return userStorage.getAllUserBooks(id);
    }

    public BookDto getBookById(Long id) {
        workCheck();
        return bookStorage.getBookById(id);
    }

    public void deleteUser(Long id) {
        workCheck();
        userStorage.deleteUser(id);
    }

    public void deleteBook(Long id) {
        workCheck();
        bookStorage.deleteBook(id);
    }

    public UserDto updateUser(UserEntity userEntity) {
        workCheck();
        return userStorage.updateUser(userEntity);
    }

    public void deleteBookFromUser(BookDto bookDto) {
        workCheck();
        userStorage.deleteBookFromUser(bookDto);
    }

    public void addBookToUser(BookDto bookDto){
        workCheck();
        userStorage.addBookToUser(bookDto);
    }

    //simulates 10% probability of database drop
    private void workCheck(){
        if (Math.random()*100<10){
            throw new CantAccessException("can't access to database");
        }
    }
}
