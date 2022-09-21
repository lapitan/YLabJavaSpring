package com.edu.ulab.app.entity;

import com.edu.ulab.app.dto.UserDto;
import lombok.Data;

import java.util.ArrayList;

@Data
public class UserEntity {
    private Long id;

    private String name;
    private String title;

    private int age;

    private ArrayList<Long> booksId;

    public UserEntity(UserDto userDto) {
        this.id=userDto.getId();
        this.name= userDto.getFullName();
        this.title= userDto.getTitle();
        this.age= userDto.getAge();
        booksId=new ArrayList<>();
    }

    public UserEntity() {
        booksId=new ArrayList<>();
    }

    public UserDto makeUserDto(){
        UserDto userDto= new UserDto();
        userDto.setId(id);
        userDto.setFullName(name);
        userDto.setTitle(title);
        userDto.setAge(age);
        return userDto;

    }

    public void putBookId(Long bookId){
        booksId.add(bookId);
    }

    public void removeConcreteBook(Long bookId){
        booksId.remove(bookId);
    }

    public void removeAllBooks(){
        booksId.clear();
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
