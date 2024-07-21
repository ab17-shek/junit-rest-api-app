package com.book.junit_rest_api_app.controller;

import com.book.junit_rest_api_app.entity.Book;
import com.book.junit_rest_api_app.service.impl.BookServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/book")
public class BookController {

    Logger LOGGER = LogManager.getLogger(BookController.class);

    @Autowired
    private BookServiceImpl bookService;

    @PostMapping("/add")
    ResponseEntity<Book> addBook(@RequestBody Book book) {
        LOGGER.info("Inside addBook : {}",book.getBookName());
        Book result = bookService.saveBook(book);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @GetMapping("/getBooks")
    //if in request good rating comes as true
    //  -> we will filter book records whose rating is equal and greater than 8
    ResponseEntity<List<Book>> getBooks(@RequestHeader boolean goodRating){
        LOGGER.info("Inside getBooks : ");
        List<Book> books =  bookService.getAllBooks(goodRating);
        LOGGER.info("Books : "+books);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }


}
