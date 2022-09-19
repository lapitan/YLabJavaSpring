package com.edu.ulab.app.entity;

import com.edu.ulab.app.dto.UserDto;

import java.util.ArrayList;
public class UserEntity {
    private Long id;

    private String name;
    private String title;

    private int age;

    private ArrayList<Long> booksId;



    public UserEntity(String name, String title, int age) {
        this.name = name;
        this.title = title;
        this.age = age;
        booksId=new ArrayList<>();
    }

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

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public ArrayList<Long> getBooksId() {
        return booksId;
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
        if (object instanceof UserEntity userEntity){
            return compareWithUser(userEntity);
        }
        if (object instanceof UserDto userDto){
            return compareWithUserDto(userDto);
        }
        if (object instanceof Long number){
            return compareWithLong(number);
        }
        return false;
    }

    private boolean compareWithUser(UserEntity user){
        if (!user.getId().equals(id)){
            return false;
        }
        if (user.getAge()!=age){
            return false;
        }
        if (!user.getName().equals(name)){
            return false;
        }
        if (!user.getTitle().equals(title)){
            return false;
        }
        if (!user.getBooksId().equals(booksId)){
            return false;
        }
        return true;
    }
    //if id==-1, id won't be used
    private boolean compareWithUserDto(UserDto userDto){
        if (userDto.getAge()!=age){
            return false;
        }
        if (!userDto.getFullName().equals(name)){
            return false;
        }
        if (!userDto.getTitle().equals(title)){
            return false;
        }
        if (userDto.getId().equals(-1L)){
            return true;
        }
        if (!userDto.getId().equals(id)){
            return false;
        }
        return true;
    }

    private boolean compareWithLong(Long id){
        return this.id.equals(id);
    }


}
