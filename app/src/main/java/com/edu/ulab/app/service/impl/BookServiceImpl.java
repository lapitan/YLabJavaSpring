package com.edu.ulab.app.service.impl;

import com.edu.ulab.app.dto.BookDto;
import com.edu.ulab.app.exception.NotFoundException;
import com.edu.ulab.app.service.BookService;
import com.edu.ulab.app.storage.Storage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class BookServiceImpl implements BookService {

    Storage storage;

    public BookServiceImpl() {
        storage = Storage.createStorage();
    }

    @Override
    public BookDto createBook(BookDto bookDto) {
        bookDto = storage.createBook(bookDto);
        log.info("create book with id: {}", bookDto.getId());
        return bookDto;
    }

    @Override
    public BookDto updateBook(BookDto bookDto) {
        try {
            bookDto = storage.createBook(bookDto);
            log.info("update book with id: {}", bookDto.getId());
            return bookDto;
        } catch (NullPointerException exception) {
            throw new NullPointerException("cant find book with id: " + bookDto.getId().toString());
        }
    }

    @Override
    public List<Long> getAllUsersBooks(Long userId) {
        try {
            List<Long> list = storage.getAllUserBooks(userId);
            log.info("get all books of user with id: {}",userId);
            return list;
        } catch (NullPointerException exception) {
            throw new NullPointerException("cant find user with id: " + userId.toString());
        }
    }

    @Override
    public BookDto getBookById(Long id) {
        try {
            BookDto bookDto=storage.getBookById(id);
            log.info("get book with id: {}",id);
            return bookDto;
        } catch (NullPointerException exception) {
            throw new NullPointerException("cant find book with id: " + id.toString());
        }
    }

    @Override
    public void deleteBookById(Long id) {
        try {
            storage.deleteBook(id);
            log.info("delete book with id: {}",id);
        } catch (NullPointerException exception) {
            throw new NullPointerException("cant find book with id: " + id.toString());
        }
    }

    @Override
    public void deleteBookFromUser(BookDto bookDto) {
        try {
            storage.deleteBookFromUser(bookDto);
            log.info("delete book with id: {} from user with id: {}",bookDto.getId(),bookDto.getUserId());
        } catch (NullPointerException exception) {
            throw new NullPointerException("cant find book with id: " + bookDto.getId().toString() + " on user with id: " + bookDto.getUserId().toString());
        }
    }
}
