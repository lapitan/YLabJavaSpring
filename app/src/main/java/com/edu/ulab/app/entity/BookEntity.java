package com.edu.ulab.app.entity;

import com.edu.ulab.app.dto.BookDto;

public class BookEntity {
    private Long id;

    private Long userId;

    private String title;

    private String author;

    private long pageCount;

    public BookEntity(Long userId, String title, String author, long pageCount) {
        this.userId = userId;
        this.title = title;
        this.author = author;
        this.pageCount = pageCount;
    }

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

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public long getPageCount() {
        return pageCount;
    }

    public void setPageCount(long pageCount) {
        this.pageCount = pageCount;
    }

    @Override
    public boolean equals(Object object){
        if (object instanceof BookEntity bookEntity){
            return compareWithBook(bookEntity);
        }
        if (object instanceof BookDto bookDto){
            return compareWithBookDto(bookDto);
        }
        if (object instanceof Long number){
            return compareWithLong(number);
        }
        return false;
    }

    private boolean compareWithBook(BookEntity book){
        if (!book.getAuthor().equals(author)){
            return false;
        }
        if (!book.getId().equals(id)){
            return false;
        }
        if (!book.getTitle().equals(title)){
            return false;
        }
        if (!book.getUserId().equals(userId)){
            return false;
        }
        if (book.getPageCount() != pageCount){
            return false;
        }
        return true;
    }
    //if id==-1, id won't be used
    private boolean compareWithBookDto(BookDto book){
        if (!book.getAuthor().equals(author)){
            return false;
        }
        if (!book.getTitle().equals(title)){
            return false;
        }
        if (!book.getUserId().equals(userId)){
            return false;
        }
        if (book.getPageCount() != pageCount){
            return false;
        }
        if (book.getId().equals(-1L)){
            return true;
        }
        if (!book.getId().equals(id)){
            return false;
        }
        return true;
    }

    private boolean compareWithLong(Long id){
        return this.id.equals(id);
    }

}
