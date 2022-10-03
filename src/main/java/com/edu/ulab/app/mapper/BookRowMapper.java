package com.edu.ulab.app.mapper;

import com.edu.ulab.app.dto.BookDto;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookRowMapper implements RowMapper<BookDto> {
    @Override
    public BookDto mapRow(ResultSet rs, int rowNum) throws SQLException {

        BookDto bookDto = new BookDto();
        bookDto.setUserId(rs.getLong("PERSON_ID"));
        bookDto.setId(rs.getLong("ID"));
        bookDto.setAuthor(rs.getString("AUTHOR"));
        bookDto.setTitle(rs.getString("TITLE"));
        bookDto.setPageCount(rs.getLong("PAGE_COUNT"));

        return bookDto;
    }
}
