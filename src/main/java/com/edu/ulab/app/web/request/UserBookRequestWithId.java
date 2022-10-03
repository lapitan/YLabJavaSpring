package com.edu.ulab.app.web.request;

import lombok.Data;

import java.util.List;

@Data
public class UserBookRequestWithId {
    private UserRequestWithId userRequest;
    private List<BookRequest> bookRequests;
}
