package com.book.junit_rest_api_app.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
public class ErrorDetail {

    private Date date;
    private String message;
    private String details;
}
