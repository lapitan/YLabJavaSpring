package com.edu.ulab.app.entity;

import com.edu.ulab.app.dto.BookDto;
import lombok.Data;

@Data
public class BookEntity {
    private Long id;

    private Long userId;

    private String title;

    private String author;

    private long pageCount;

    public BookEntity(BookDto bookDto) {
        this.id=bookDto.getId();
        this.userId = bookDto.getUserId();
        this.title = bookDto.getTitle();
        this.author = bookDto.getAuthor();
        this.pageCount = bookDto.getPageCount();
    }

    public BookEntity() {

    }

    public BookDto makeBookDto(){
        BookDto bookDto= new BookDto();
        bookDto.setId(id);
        bookDto.setUserId(userId);
        bookDto.setTitle(title);
        bookDto.setAuthor(author);
        bookDto.setPageCount(pageCount);
        return bookDto;
    }

    @Override
    public boolean equals(Object object){
        if (object instanceof Long number){
            return compareWithLong(number);
        }
        return false;
    }

    private boolean compareWithLong(Long id){
        return this.id.equals(id);
    }

}
