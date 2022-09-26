package com.edu.ulab.app.mapper;

import com.edu.ulab.app.dto.UserDto;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<UserDto> {

    @Override
    public UserDto mapRow(ResultSet rs, int rowNum) throws SQLException {

        UserDto userDto = new UserDto();
        userDto.setId(rs.getLong("ID"));
        userDto.setTitle(rs.getString("TITLE"));
        userDto.setFullName(rs.getString("FULL_NAME"));
        userDto.setAge(rs.getInt("AGE"));

        return userDto;
    }
}
