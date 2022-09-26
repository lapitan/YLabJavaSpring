package com.edu.ulab.app.service.impl;

import com.edu.ulab.app.dto.BookDto;
import com.edu.ulab.app.exception.NotFoundException;
import com.edu.ulab.app.mapper.BookMapperImpl;
import com.edu.ulab.app.mapper.BookRowMapper;
import com.edu.ulab.app.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class BookServiceImplTemplate implements BookService {

    private final JdbcTemplate jdbcTemplate;

    public BookServiceImplTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public BookDto createBook(BookDto bookDto) {
        log.info("Create book: {}", bookDto);
        try {
            final String INSERT_SQL = "INSERT INTO BOOK(TITLE, AUTHOR, PAGE_COUNT, PERSON_ID) VALUES (?,?,?,?)";
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(
                    connection -> {
                        PreparedStatement ps =
                                connection.prepareStatement(INSERT_SQL, new String[]{"id"});
                        ps.setString(1, bookDto.getTitle());
                        ps.setString(2, bookDto.getAuthor());
                        ps.setLong(3, bookDto.getPageCount());
                        ps.setLong(4, bookDto.getUserId());
                        return ps;
                    },
                    keyHolder);

            bookDto.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
        } catch (DataIntegrityViolationException exception) {
            throw new NotFoundException("can't add book to user: " + bookDto.getUserId());
        }
        return bookDto;
    }

    @Override
    public BookDto updateBook(BookDto bookDto) {
        log.info("Update book: {}", bookDto);
        return createBook(bookDto);
    }

    @Override
    public BookDto getBookById(Long id) {
        log.info("Get book by id: {}", id);
        final String SQL_QUERY = "SELECT * FROM BOOK WHERE ID=?";
        return jdbcTemplate.queryForObject(SQL_QUERY, new BookRowMapper(), id);
    }

    @Override
    public void deleteBookById(Long id) {
        log.info("Delete book by id: {}", id);
        final String SQL_QUERY = "DELETE FROM BOOK WHERE ID=?";
        jdbcTemplate.update(SQL_QUERY, id);
    }

    @Override
    public List<BookDto> getAllUsersBooks(Long userId) {
        log.info("Get all books of user with id: {}", userId);
        final String SQL_QUERY = "SELECT * FROM BOOK WHERE PERSON_ID=?";
        return jdbcTemplate.query(SQL_QUERY, new BookRowMapper(), userId);
    }
}
