package com.edu.ulab.app.facade;

import com.edu.ulab.app.dto.BookDto;
import com.edu.ulab.app.dto.UserDto;
import com.edu.ulab.app.mapper.BookMapper;
import com.edu.ulab.app.mapper.UserMapper;
import com.edu.ulab.app.service.impl.BookServiceImpl;
import com.edu.ulab.app.service.impl.BookServiceImplTemplate;
import com.edu.ulab.app.service.impl.UserServiceImpl;
import com.edu.ulab.app.service.impl.UserServiceImplTemplate;
import com.edu.ulab.app.web.request.UserBookRequest;
import com.edu.ulab.app.web.request.UserBookRequestWithId;
import com.edu.ulab.app.web.response.UserBookResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@Slf4j
@Component
public class UserDataFacade {
    private final UserServiceImpl userService;
    private final BookServiceImpl  bookService;
    private final UserMapper userMapper;
    private final BookMapper bookMapper;

    public UserDataFacade(UserServiceImpl userService,
                          BookServiceImpl bookService,
                          UserMapper userMapper,
                          BookMapper bookMapper) {
        this.userService = userService;
        this.bookService = bookService;
        this.userMapper = userMapper;
        this.bookMapper = bookMapper;
    }

    public UserBookResponse createUserWithBooks(UserBookRequest userBookRequest) {
        log.info("Got user book create request: {}", userBookRequest);
        @Valid UserDto userDto = userMapper.userRequestToUserDto(userBookRequest.getUserRequest());
        log.info("Mapped user request: {}", userDto);

        UserDto createdUser = userService.createUser(userDto);
        log.info("Created user: {}", createdUser);

        List<Long> bookIdList = userBookRequest.getBookRequests()
                .stream()
                .filter(Objects::nonNull)
                .map(bookMapper::bookRequestToBookDto)
                .peek(bookDto -> bookDto.setUserId(createdUser.getId()))
                .peek(mappedBookDto -> log.info("mapped book: {}", mappedBookDto))
                .map(bookService::createBook)
                .peek(createdBook -> log.info("Created book: {}", createdBook))
                .map(BookDto::getId)
                .toList();
        log.info("Collected book ids: {}", bookIdList);

        return UserBookResponse.builder()
                .userId(createdUser.getId())
                .booksIdList(bookIdList)
                .build();
    }

    public UserBookResponse updateUserWithBooks(UserBookRequestWithId userBookRequest) {
        log.info("Got user book update request: {}", userBookRequest);
        UserDto userDto = userMapper.userRequestWithIdToUserDto(userBookRequest.getUserRequest());
        log.info("Mapped user request: {}", userDto);
        UserDto finalUserDto = userDto;
        List<BookDto> bookDtoList = userBookRequest.getBookRequests()
                .stream()
                .filter(Objects::nonNull)
                .map(bookMapper::bookRequestToBookDto)
                .peek(bookDto -> bookDto.setUserId(finalUserDto.getId()))
                .toList();

        List<BookDto> userBookIds = bookService.getAllUsersBooks(userDto.getId());

        userBookIds.stream()
                .map(BookDto::getId)
                .forEach(bookService::deleteBookById);

        userDto = userService.updateUser(userDto);
        bookDtoList.forEach(bookService::createBook);

        return UserBookResponse.builder()
                .userId(userDto.getId())
                .booksIdList(bookService.getAllUsersBooks(userDto.getId())
                        .stream()
                        .map(BookDto::getId)
                        .toList())
                .build();
    }

    public UserBookResponse getUserWithBooks(Long userId) {
        log.info("Got get user with book request: {}", userId);

        UserDto userDto = userService.getUserById(userId);
        log.info("Find user: {}", userDto);

        List<BookDto> userBookIds = bookService.getAllUsersBooks(userId);
        log.info("Find book ids: {}", userBookIds);

        return UserBookResponse.builder()
                .userId(userDto.getId())
                .booksIdList(userBookIds.stream()
                        .map(BookDto::getId)
                        .toList())
                .build();
    }

    public void deleteUserWithBooks(Long userId) {
        log.info("Got delete user with book request: {}", userId);

        List<BookDto> userBookIds = bookService.getAllUsersBooks(userId);
        log.info("Delete book ids: {}", userBookIds);

        userBookIds.stream()
                .map(BookDto::getId)
                .forEach(bookService::deleteBookById);

        UserDto userDto = userService.getUserById(userId);
        log.info("Delete user: {}", userDto);
        userService.deleteUserById(userId);
    }
}
