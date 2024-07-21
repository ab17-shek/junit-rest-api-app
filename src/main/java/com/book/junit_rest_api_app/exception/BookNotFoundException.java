package com.book.junit_rest_api_app.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@Setter
@ResponseStatus(HttpStatus.NOT_FOUND)
public class BookNotFoundException extends RuntimeException{

    private String message;

    public BookNotFoundException(String message) {
        super(message);
        this.message = message;
    }
}
