package com.edu.ulab.app.service;

import com.edu.ulab.app.dto.BookDto;
import com.edu.ulab.app.dto.UserDto;
import com.edu.ulab.app.entity.Book;
import com.edu.ulab.app.entity.Person;
import com.edu.ulab.app.exception.CreateException;
import com.edu.ulab.app.exception.NotFoundException;
import com.edu.ulab.app.mapper.BookMapper;
import com.edu.ulab.app.mapper.UserMapper;
import com.edu.ulab.app.repository.BookRepository;
import com.edu.ulab.app.repository.UserRepository;
import com.edu.ulab.app.service.impl.BookServiceImpl;
import com.edu.ulab.app.service.impl.UserServiceImpl;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

/**
 * Тестирование функционала {@link com.edu.ulab.app.service.impl.BookServiceImpl}.
 */
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@DisplayName("Testing book functionality.")
@RunWith(MockitoJUnitRunner.class)
public class BookServiceImplTest {

    @Mock
    BookRepository bookRepository;

    @Mock
    BookMapper bookMapper;

    @Mock
    UserRepository userRepository;

    @InjectMocks
    BookServiceImpl bookService;

    @Test
    @DisplayName("Сохранение книги. Должно пройти успешно.")
    public void saveBook_Test() {
        //given

        Person person = new Person();
        person.setId(1L);
        person.setAge(111);
        person.setTitle("reader");
        person.setFullName("Test Test");

        Optional<Person> optionalPerson = Optional.of(person);

        BookDto bookDto = new BookDto();
        bookDto.setUserId(1L);
        bookDto.setAuthor("test author");
        bookDto.setTitle("test title");
        bookDto.setPageCount(1000);

        BookDto result = new BookDto();
        result.setId(1L);
        result.setUserId(1L);
        result.setAuthor("test author");
        result.setTitle("test title");
        result.setPageCount(1000);

        Book book = new Book();
        book.setPageCount(1000);
        book.setTitle("test title");
        book.setAuthor("test author");
        book.setPerson(person);

        Book savedBook = new Book();
        savedBook.setId(1L);
        savedBook.setPageCount(1000);
        savedBook.setTitle("test title");
        savedBook.setAuthor("test author");
        savedBook.setPerson(person);

        //when

        when(bookMapper.bookDtoToBook(bookDto)).thenReturn(book);
        when(userRepository.findById(1L)).thenReturn(optionalPerson);
        when(bookRepository.save(book)).thenReturn(savedBook);
        when(bookMapper.bookToBookDto(savedBook)).thenReturn(result);


        //then
        BookDto bookDtoResult = bookService.updateBook(bookDto);
        assertEquals(1L, bookDtoResult.getId());
    }

    //тест у апдейта и криэйта почти одинаковый (просто вызывается другой метод)

    @Test
    @DisplayName("Обновление книги. Должно пройти успешно.")
    public void updateBook_Test() {
        //given

        Person person = new Person();
        person.setId(1L);
        person.setAge(111);
        person.setTitle("reader");
        person.setFullName("Test Test");

        Optional<Person> optionalPerson = Optional.of(person);

        BookDto bookDto = new BookDto();
        bookDto.setUserId(1L);
        bookDto.setAuthor("test author");
        bookDto.setTitle("test title");
        bookDto.setPageCount(1000);

        BookDto result = new BookDto();
        result.setId(1L);
        result.setUserId(1L);
        result.setAuthor("test author");
        result.setTitle("test title");
        result.setPageCount(1000);

        Book book = new Book();
        book.setPageCount(1000);
        book.setTitle("test title");
        book.setAuthor("test author");
        book.setPerson(person);

        Book savedBook = new Book();
        savedBook.setId(1L);
        savedBook.setPageCount(1000);
        savedBook.setTitle("test title");
        savedBook.setAuthor("test author");
        savedBook.setPerson(person);

        //when

        when(bookMapper.bookDtoToBook(bookDto)).thenReturn(book);
        when(userRepository.findById(1L)).thenReturn(optionalPerson);
        when(bookRepository.save(book)).thenReturn(savedBook);
        when(bookMapper.bookToBookDto(savedBook)).thenReturn(result);


        //then
        BookDto bookDtoResult = bookService.updateBook(bookDto);
        assertEquals(1L, bookDtoResult.getId());
    }

    @Test
    @DisplayName("Получение книги. Должно пройти успешно.")
    public void getBook_Test() {
        //given

        Person person = new Person();
        person.setId(1L);
        person.setAge(111);
        person.setTitle("reader");
        person.setFullName("Test Test");

        BookDto result = new BookDto();
        result.setId(1L);
        result.setUserId(1L);
        result.setAuthor("test author");
        result.setTitle("test title");
        result.setPageCount(1000);

        Book book = new Book();
        book.setId(1L);
        book.setPageCount(1000);
        book.setTitle("test title");
        book.setAuthor("test author");
        book.setPerson(person);

        Optional<Book> optionalBook = Optional.of(book);

        Long id = 1L;

        //when

        when(bookRepository.findById(1L)).thenReturn(optionalBook);
        when(bookMapper.bookToBookDto(book)).thenReturn(result);


        //then
        BookDto bookDtoResult = bookService.getBookById(id);
        assertEquals(1L, bookDtoResult.getId());
    }

    @Test
    @DisplayName("Получение всех книг. Должно пройти успешно.")
    public void getAllBooks_Test() {
        //given

        Person person = new Person();
        person.setId(1L);
        person.setAge(111);
        person.setTitle("reader");
        person.setFullName("Test Test");

        BookDto bookDto1 = new BookDto();
        bookDto1.setId(1L);
        bookDto1.setUserId(1L);
        bookDto1.setAuthor("test author");
        bookDto1.setTitle("test title");
        bookDto1.setPageCount(1000);

        BookDto bookDto2 = new BookDto();
        bookDto2.setId(2L);
        bookDto2.setUserId(1L);
        bookDto2.setAuthor("test author2");
        bookDto2.setTitle("test title2");
        bookDto2.setPageCount(1000);

        Book book1 = new Book();
        book1.setId(1L);
        book1.setPageCount(1000);
        book1.setTitle("test title");
        book1.setAuthor("test author");
        book1.setPerson(person);

        Book book2 = new Book();
        book2.setId(2L);
        book2.setPageCount(1000);
        book2.setTitle("test title2");
        book2.setAuthor("test author2");
        book2.setPerson(person);

        ArrayList<Book> bookIterable = new ArrayList<>();
        bookIterable.add(book1);
        bookIterable.add(book2);

        Long id = 1L;

        //when

        when(bookRepository.findAll()).thenReturn(bookIterable);
        when(bookMapper.bookToBookDto(book1)).thenReturn(bookDto1);
        when(bookMapper.bookToBookDto(book2)).thenReturn(bookDto2);


        //then
        List<BookDto> bookDtoListResult = bookService.getAllUsersBooks(id);
        assertEquals(2, bookDtoListResult.size());
    }

    @Test
    @DisplayName("Удаление книги. Должно пройти успешно.")
    public void deleteBook_Test() {
        //given

        Person person = new Person();
        person.setId(1L);
        person.setAge(111);
        person.setTitle("reader");
        person.setFullName("Test Test");

        BookDto bookDto = new BookDto();
        bookDto.setId(1L);
        bookDto.setUserId(1L);
        bookDto.setAuthor("test author");
        bookDto.setTitle("test title");
        bookDto.setPageCount(1000);

        Book book = new Book();
        book.setId(1L);
        book.setPageCount(1000);
        book.setTitle("test title");
        book.setAuthor("test author");
        book.setPerson(person);

        ArrayList<Book> bookIterable = new ArrayList<>();
        bookIterable.add(book);

        Long id = 1L;

        //when

        when(bookRepository.findAll()).thenReturn(bookIterable);
        when(bookMapper.bookToBookDto(book)).thenReturn(bookDto);
        List<BookDto> bookDtoListResultBefore = bookService.getAllUsersBooks(id);
        bookIterable = new ArrayList<>();
        when(bookRepository.findAll()).thenReturn(bookIterable);

        //then
        bookService.deleteBookById(id);
        List<BookDto> bookDtoListResultAfter = bookService.getAllUsersBooks(id);

        assertEquals(1, bookDtoListResultBefore.size());
        assertEquals(0, bookDtoListResultAfter.size());
    }

    // * failed

    @Test
    //тут тоже
    @DisplayName("Получение несуществующей книги. Должно пройти успешно, но неудачно (???).")
    public void getNotExistingBook_Test() {
        //given

        Long id = 1L;

        //when

        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        //then

        assertThatThrownBy(()->bookService.getBookById(id))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("cant find book with id: 1");
    }

    @Test
    //тут тоже
    @DisplayName("Получение несуществующей книги. Должно пройти успешно, но неудачно (???).")
    public void saveBookWithNotExistingUser_Test() {
        //given

        BookDto bookDto = new BookDto();
        bookDto.setUserId(1L);
        bookDto.setAuthor("test author");
        bookDto.setTitle("test title");
        bookDto.setPageCount(1000);

        BookDto result = new BookDto();
        result.setId(1L);
        result.setUserId(1L);
        result.setAuthor("test author");
        result.setTitle("test title");
        result.setPageCount(1000);

        Book book = new Book();
        book.setPageCount(1000);
        book.setTitle("test title");
        book.setAuthor("test author");
        book.setPerson(null);

        Book savedBook = new Book();
        savedBook.setId(1L);
        savedBook.setPageCount(1000);
        savedBook.setTitle("test title");
        savedBook.setAuthor("test author");
        savedBook.setPerson(null);

        //when

        when(bookMapper.bookDtoToBook(bookDto)).thenReturn(book);
        when(userRepository.findById(1L)).thenReturn(Optional.empty());


        //then

        assertThatThrownBy(()->bookService.createBook(bookDto))
                .isInstanceOf(CreateException.class)
                .hasMessage("cant add book to user with id: 1");
    }
}
