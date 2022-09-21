package com.edu.ulab.app.facade;

import com.edu.ulab.app.dto.BookDto;
import com.edu.ulab.app.dto.UserDto;
import com.edu.ulab.app.entity.UserEntity;
import com.edu.ulab.app.exception.CantAccessException;
import com.edu.ulab.app.exception.DataBaseException;
import com.edu.ulab.app.exception.NotFoundException;
import com.edu.ulab.app.mapper.BookMapper;
import com.edu.ulab.app.mapper.UserMapper;
import com.edu.ulab.app.service.BookService;
import com.edu.ulab.app.service.UserService;
import com.edu.ulab.app.web.request.UserBookRequest;
import com.edu.ulab.app.web.request.UserBookRequestWithId;
import com.edu.ulab.app.web.response.UserBookResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Component
public class UserDataFacade {
    private final UserService userService;
    private final BookService bookService;
    private final UserMapper userMapper;
    private final BookMapper bookMapper;

    public UserDataFacade(UserService userService,
                          BookService bookService,
                          UserMapper userMapper,
                          BookMapper bookMapper) {
        this.userService = userService;
        this.bookService = bookService;
        this.userMapper = userMapper;
        this.bookMapper = bookMapper;
    }

    public UserBookResponse createUserWithBooks(UserBookRequest userBookRequest) {
        try {
            log.info("Got user book create request: {}", userBookRequest);
            UserDto userDto = userMapper.userRequestToUserDto(userBookRequest.getUserRequest());
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
                    .peek(userService::addBookToUser)
                    .peek(createdBook -> log.info("Created book: {}", createdBook))
                    .map(BookDto::getId)
                    .toList();

            log.info("Collected book ids: {}", bookIdList);

            return UserBookResponse.builder()
                    .userId(createdUser.getId())
                    .booksIdList(bookService.getAllUsersBooks(createdUser.getId()))
                    .build();
        }catch (CantAccessException exception) {
                throw new DataBaseException(exception.getMessage());
            }
    }

    public UserBookResponse updateUserWithBooks(UserBookRequestWithId userBookRequest) {
        log.info("Got user book update request: {}", userBookRequest);
        UserDto userDto = userMapper.userRequestWithIdToUserDto(userBookRequest.getUserRequest());
        log.info("Mapped user request: {}", userDto);
        try {

            UserDto finalUserDto = userDto;
            List<BookDto> bookDtoList = userBookRequest.getBookRequests()
                    .stream()
                    .filter(Objects::nonNull)
                    .map(bookMapper::bookRequestToBookDto)
                    .peek(bookDto -> bookDto.setUserId(finalUserDto.getId()))
                    .toList();

            List<Long> userBookIds = bookService.getAllUsersBooks(userDto.getId());

//        userBookIds.forEach(bookService::deleteBookById);

            while (!userBookIds.isEmpty()) {
                Long id = userBookIds.get(0);
                bookService.deleteBookFromUser(bookService.getBookById(id));
                bookService.deleteBookById(id);
            }

            UserEntity userEntity = userMapper.userDtoToUserEntity(userDto);

            userDto = userService.updateUser(userEntity);
            bookDtoList.forEach(bookService::createBook);
            bookDtoList.forEach(userService::addBookToUser);

            return UserBookResponse.builder()
                    .userId(userDto.getId())
                    .booksIdList(bookService.getAllUsersBooks(userDto.getId()))
                    .build();
        } catch (NullPointerException | CantAccessException exception) {
            throw new NotFoundException(exception.getMessage());
        }

    }

    public UserBookResponse getUserWithBooks(Long userId) {
        try {
            log.info("Got get user with book request: {}", userId);

            UserDto userDto = userService.getUserById(userId);
            log.info("Find user: {}", userDto);

            List<Long> userBookIds = bookService.getAllUsersBooks(userId);
            log.info("Find book ids: {}", userBookIds);

            return UserBookResponse.builder()
                    .userId(userDto.getId())
                    .booksIdList(userBookIds)
                    .build();
        } catch (NullPointerException | CantAccessException exception) {
            throw new NotFoundException(exception.getMessage());
        }
    }

    public void deleteUserWithBooks(Long userId) {
        try {
            log.info("Got delete user with book request: {}", userId);

            List<Long> userBookIds = bookService.getAllUsersBooks(userId);
            log.info("Delete book ids: {}", userBookIds);

            userBookIds.forEach(bookService::deleteBookById);

            UserDto userDto = userService.getUserById(userId);
            log.info("Delete user: {}", userDto);
            userService.deleteUserById(userId);
        } catch (NullPointerException | CantAccessException exception) {
            throw new NotFoundException(exception.getMessage());
        }
    }
}
