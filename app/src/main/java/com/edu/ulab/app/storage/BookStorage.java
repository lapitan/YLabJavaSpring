package com.edu.ulab.app.storage;

import com.edu.ulab.app.dto.BookDto;
import com.edu.ulab.app.entity.BookEntity;
import com.edu.ulab.app.exception.NotFoundException;

import java.util.HashMap;

public class BookStorage {

    private HashMap<Long, BookEntity> bookEntities;
    private Long currBookId;

    public BookStorage() {
        currBookId = 0L;
        bookEntities = new HashMap<>();
    }

    public BookDto createBook(BookDto bookDto) {
        bookDto.setId(currBookId);
        currBookId++;
        BookEntity bookEntity = new BookEntity(bookDto);
        bookEntities.put(bookEntity.getId(), bookEntity);
        bookDto = bookEntity.makeBookDto();

        return bookDto;
    }

    public BookDto getBookById(Long id) {
        return bookEntities.get(id).makeBookDto();
    }

    public void deleteBook(Long id) {
        bookEntities.remove(id);
    }
}
