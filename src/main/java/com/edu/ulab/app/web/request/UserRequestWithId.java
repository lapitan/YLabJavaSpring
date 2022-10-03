package com.edu.ulab.app.web.request;

import lombok.Data;

@Data
public class UserRequestWithId {
    private Long id;
    private String fullName;
    private String title;
    private int age;
}
