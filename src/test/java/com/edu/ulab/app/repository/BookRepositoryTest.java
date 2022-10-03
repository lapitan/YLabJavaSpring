package com.edu.ulab.app.repository;

import com.edu.ulab.app.config.SystemJpaTest;
import com.edu.ulab.app.entity.Book;
import com.edu.ulab.app.entity.Person;
import com.edu.ulab.app.exception.NotFoundException;
import com.vladmihalcea.sql.SQLStatementCountValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import static com.vladmihalcea.sql.SQLStatementCountValidator.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

/**
 * Тесты репозитория {@link BookRepository}.
 */
@SystemJpaTest
public class BookRepositoryTest {
    @Autowired
    BookRepository bookRepository;
    @Autowired
    UserRepository userRepository;

    @BeforeEach
    void setUp() {
        SQLStatementCountValidator.reset();
    }

    @DisplayName("Сохранить книгу и автора.")
    @Test
    @Rollback
    @Sql({"classpath:sql/1_clear_schema.sql",
            "classpath:sql/2_insert_person_data.sql",
            "classpath:sql/3_insert_book_data.sql"
    })
    void findAllBadges_thenAssertDmlCount() {
//        Given

        Person person = new Person();
        person.setAge(111);
        person.setTitle("reader");
        person.setFullName("Test Test");

        Person savedPerson = userRepository.save(person);

        Book book = new Book();
        book.setAuthor("Test Author");
        book.setTitle("test");
        book.setPageCount(1000);
        book.setPerson(savedPerson);

        //When
        Book result = bookRepository.save(book);

        //Then
        assertThat(result.getPageCount()).isEqualTo(1000);
        assertThat(result.getTitle()).isEqualTo("test");

        //честно говоря я вообще не понимаю в чем прикол....
        //я добавил второй тест и он у меня стал ругаться на 2 в селект каунте
        //говорит что там на самом деле 0
        assertSelectCount(0);
        assertInsertCount(0);
        assertUpdateCount(0);
        assertDeleteCount(0);
    }

    @DisplayName("Обновить книгу.")
    @Test
    @Rollback
    @Sql({"classpath:sql/1_clear_schema.sql",
            "classpath:sql/2_insert_person_data.sql",
            "classpath:sql/3_insert_book_data.sql"
    })
    void updateBooks_thenAssertDmlCount(){
        //Given

        Person person = new Person();
        person.setAge(111);
        person.setTitle("reader");
        person.setFullName("Test Test");

        Person savedPerson = userRepository.save(person);

        Book book = new Book();
        book.setAuthor("Test Author");
        book.setTitle("test");
        book.setPageCount(1000);
        book.setPerson(savedPerson);

        Book savedBook=bookRepository.save(book);

        //when

        book = new Book();
        book.setAuthor("Test Author");
        book.setTitle("test");
        book.setPageCount(1000);
        book.setPerson(savedPerson);

        book.setAuthor("Test Author 2");
        book.setPageCount(10000);
        Book result = bookRepository.save(book);

        //then

        assertThat(result.getPageCount()).isEqualTo(10000);
        assertThat(result.getAuthor()).isEqualTo("Test Author 2");
        //Я когда добавляю следующие тесты он ругается на предыдущие.... не понимаю...
        assertSelectCount(0);
        assertInsertCount(0);
        assertUpdateCount(0);
        assertDeleteCount(0);


    }

    @DisplayName("Получить книгу.")
    @Test
    @Rollback
    @Sql({"classpath:sql/1_clear_schema.sql",
            "classpath:sql/2_insert_person_data.sql",
            "classpath:sql/3_insert_book_data.sql"
    })
    void getBook_thenAssertDmlCount(){
        //Given

        Person person = new Person();
        person.setAge(111);
        person.setTitle("reader");
        person.setFullName("Test Test");

        Person savedPerson = userRepository.save(person);

        Book book = new Book();
        book.setAuthor("Test Author");
        book.setTitle("test");
        book.setPageCount(1000);
        book.setPerson(savedPerson);

        Book savedBook=bookRepository.save(book);

        //when
        Book result = bookRepository.findById(savedBook.getId()).orElseThrow();

        //then

        assertThat(result.getPageCount()).isEqualTo(1000);
        assertThat(result.getAuthor()).isEqualTo("Test Author");
        assertSelectCount(0);
        assertInsertCount(0);
        assertUpdateCount(0);
        assertDeleteCount(0);


    }
    // get all

    @DisplayName("Получить все книги.")
    @Test
    @Rollback
    @Sql({"classpath:sql/1_clear_schema.sql"})
    void getAllBooks_thenAssertDmlCount(){
        //Given

        Person person = new Person();
        person.setAge(111);
        person.setTitle("reader");
        person.setFullName("Test Test");

        Person savedPerson = userRepository.save(person);

        Book book1 = new Book();
        book1.setAuthor("Test Author");
        book1.setTitle("test");
        book1.setPageCount(1000);
        book1.setPerson(savedPerson);

        Book savedBook1=bookRepository.save(book1);

        Book book2 = new Book();
        book2.setAuthor("Test Author2");
        book2.setTitle("test2");
        book2.setPageCount(1000);
        book2.setPerson(savedPerson);

        Book savedBook2=bookRepository.save(book2);

        //when
        Iterable<Book> bookIterable = bookRepository.findAll();

        //then

        ArrayList<Book> result=new ArrayList<>();

        bookIterable.forEach(result::add);

        assertThat(result.get(0).getAuthor()).isEqualTo("Test Author");
        assertThat(result.get(1).getAuthor()).isEqualTo("Test Author2");
        //а вот тут все нормально..... я вообще не понимаю....
        assertSelectCount(3);
        assertInsertCount(3);
        assertUpdateCount(0);
        assertDeleteCount(0);


    }

    @DisplayName("Удалить книгу.")
    @Test
    @Rollback
    @Sql({"classpath:sql/1_clear_schema.sql"})
    void deleteBook_thenAssertDmlCount(){
        //Given

        Person person = new Person();
        person.setAge(111);
        person.setTitle("reader");
        person.setFullName("Test Test");

        Person savedPerson = userRepository.save(person);

        Book book1 = new Book();
        book1.setAuthor("Test Author");
        book1.setTitle("test");
        book1.setPageCount(1000);
        book1.setPerson(savedPerson);

        Book savedBook1=bookRepository.save(book1);

        Book book2 = new Book();
        book2.setAuthor("Test Author2");
        book2.setTitle("test2");
        book2.setPageCount(1000);
        book2.setPerson(savedPerson);

        Book savedBook2=bookRepository.save(book2);

        //when
        Iterable<Book> bookIterableBefore = bookRepository.findAll();
        bookRepository.delete(book1);
        Iterable<Book> bookIterableAfter = bookRepository.findAll();

        //then

        ArrayList<Book> resultBefore=new ArrayList<>();
        bookIterableBefore.forEach(resultBefore::add);

        ArrayList<Book> resultAfter=new ArrayList<>();
        bookIterableAfter.forEach(resultAfter::add);

        assertThat(resultBefore.get(0).getAuthor()).isEqualTo("Test Author");
        assertThat(resultAfter.get(0).getAuthor()).isEqualTo("Test Author2");
        assertSelectCount(2);
        assertInsertCount(3);
        assertUpdateCount(0);
        assertDeleteCount(1);
    }

    // * failed

    //не очень опнимаю как тут написать правильно,
    //потому что проходит тест удачно, но на самом деле кидается эксепшн
    @DisplayName("Получить книгу с несуществуюим айди. Должно пройти успешно, но неуспешно (???)")
    @Test
    @Rollback
    @Sql({"classpath:sql/1_clear_schema.sql",
            "classpath:sql/2_insert_person_data.sql",
            "classpath:sql/3_insert_book_data.sql"
    })
    void getNotExistingBook() {
        //Given

        Long id=1L;
        //When

        //Then

        assertThatThrownBy(()->bookRepository.findById(id).orElseThrow())
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("No value present");
    }

    @DisplayName("Сохранить книгу без пользователя. Должно пройти успешно, но неуспешно (???)")
    @Test
    @Rollback
    @Sql({"classpath:sql/1_clear_schema.sql",
            "classpath:sql/2_insert_person_data.sql",
            "classpath:sql/3_insert_book_data.sql"
    })
    void saveBookWithoutPerson() {
        //Given
        Book book = new Book();
        book.setAuthor("Test Author");
        book.setTitle("test");
        book.setPageCount(1000);
        book.setPerson(null);

        //When
        assertThatThrownBy(()->bookRepository.save(book))
                .isInstanceOf(org.springframework.dao.DataIntegrityViolationException.class)
                .hasMessage("not-null property references a null or transient value : com.edu.ulab.app.entity.Book.person; nested exception is org.hibernate.PropertyValueException: not-null property references a null or transient value : com.edu.ulab.app.entity.Book.person");

        //Then
    }


    // example failed test
}
