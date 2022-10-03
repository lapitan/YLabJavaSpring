package com.edu.ulab.app.service.impl;

import com.edu.ulab.app.dto.BookDto;
import com.edu.ulab.app.entity.Book;
import com.edu.ulab.app.entity.Person;
import com.edu.ulab.app.exception.CreateException;
import com.edu.ulab.app.exception.NotFoundException;
import com.edu.ulab.app.mapper.BookMapper;
import com.edu.ulab.app.repository.BookRepository;
import com.edu.ulab.app.repository.UserRepository;
import com.edu.ulab.app.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    private final UserRepository userRepository;

    private final BookMapper bookMapper;

    public BookServiceImpl(BookRepository bookRepository,
                           BookMapper bookMapper,
                           UserRepository userRepository) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
        this.userRepository=userRepository;
    }

    @Override
    public BookDto createBook(BookDto bookDto) {
        Book book = bookMapper.bookDtoToBook(bookDto);
        log.info("Mapped book: {}", book);
        Person person= userRepository.findById(bookDto.getUserId()).orElseThrow(
                ()-> new CreateException("cant add book to user with id: "+bookDto.getUserId()));
        book.setPerson(person);
        Book savedBook = bookRepository.save(book);
        log.info("Saved book: {}", savedBook);
        return bookMapper.bookToBookDto(savedBook);
    }

    @Override
    public BookDto updateBook(BookDto bookDto) {
        // реализовать недстающие методы
        log.info("Update book: {}", bookDto);
        return createBook(bookDto);
    }

    @Override
    public BookDto getBookById(Long id) {
        // реализовать недстающие методы
        log.info("Get book by id: {}", id);
        return bookMapper.bookToBookDto(bookRepository.findById(id).orElseThrow(
                ()-> new NotFoundException("cant find book with id: "+id)));
    }

    @Override
    public void deleteBookById(Long id) {
        // реализовать недстающие методы
        log.info("Delete book by id: {}", id);
        bookRepository.deleteById(id);
    }

    @Override
    public List<BookDto> getAllUsersBooks(Long userId) {
        log.info("Get all books of user with id: {}", userId);
        List<BookDto> result= new ArrayList<>();
        bookRepository.findAll().forEach(book -> {
            if (book.getPerson().getId().equals(userId)){
                result.add(bookMapper.bookToBookDto(book));
            }
        });
        return result;
    }
}
