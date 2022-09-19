package com.edu.ulab.app.service.impl;

import com.edu.ulab.app.dto.BookDto;
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
        storage=Storage.createStorage();
    }
    @Override
    public BookDto createBook(BookDto bookDto) {
        bookDto= storage.createBook(bookDto);
        return bookDto;
    }

    @Override
    public BookDto updateBook(BookDto bookDto) {
        return storage.createBook(bookDto);
    }

    @Override
    public List<Long> getAllUsersBooks(Long userId) {
        return storage.getAllUserBooks(userId);
    }

    @Override
    public BookDto getBookById(Long id) {
        return storage.getBookById(id);
    }

    @Override
    public void deleteBookById(Long id) {
        storage.deleteBook(id);
    }

    @Override
    public void deleteBookFromUser(BookDto bookDto) {
        storage.deleteBookFromUser(bookDto);
    }
}
