package com.book.junit_rest_api_app.handler;

import com.book.junit_rest_api_app.exception.BookNotFoundException;
import com.book.junit_rest_api_app.model.ErrorDetail;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<ErrorDetail> bookNotFoundException(BookNotFoundException bookNotFoundException, WebRequest webRequest) {
        ErrorDetail errorDetail = new ErrorDetail(new Date(), bookNotFoundException.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetail, HttpStatus.NOT_FOUND);

    }
}
