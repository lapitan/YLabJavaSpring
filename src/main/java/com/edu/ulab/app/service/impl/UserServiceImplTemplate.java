package com.edu.ulab.app.service.impl;

import com.edu.ulab.app.dto.BookDto;
import com.edu.ulab.app.dto.UserDto;
import com.edu.ulab.app.exception.NotFoundException;
import com.edu.ulab.app.mapper.UserRowMapper;
import com.edu.ulab.app.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;

@Slf4j
@Service
public class UserServiceImplTemplate implements UserService {
    private final JdbcTemplate jdbcTemplate;

    public UserServiceImplTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        log.info("Create user: {}", userDto);
        final String INSERT_SQL = "INSERT INTO PERSON(FULL_NAME, TITLE, AGE) VALUES (?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps = connection.prepareStatement(INSERT_SQL, new String[]{"id"});
                    ps.setString(1, userDto.getFullName());
                    ps.setString(2, userDto.getTitle());
                    ps.setLong(3, userDto.getAge());
                    return ps;
                }, keyHolder);

        userDto.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
        return userDto;
    }

    @Override
    public UserDto updateUser(UserDto userDto) {
        log.info("Update user: {}", userDto);
        final String SQL_QUERY="UPDATE PERSON SET AGE=?, FULL_NAME=?, TITLE=? WHERE ID=?";
        jdbcTemplate.update(SQL_QUERY,userDto.getAge(),userDto.getFullName(),userDto.getTitle(),userDto.getId());
        return userDto;
    }

    @Override
    public UserDto getUserById(Long id) {
        log.info("Get user by id: {}", id);
        try {
            final String SQL_QUERY = "SELECT * FROM PERSON WHERE ID=?";
            return jdbcTemplate.queryForObject(SQL_QUERY, new UserRowMapper(), id);
        }catch (EmptyResultDataAccessException exception){
            throw new NotFoundException("Can't find user with id: "+id);
        }
    }

    @Override
    public void deleteUserById(Long id) {
        log.info("Delete user by id: {}", id);
        final String SQL_QUERY="DELETE FROM PERSON WHERE ID=?";
        jdbcTemplate.update(SQL_QUERY,id);
    }
}
