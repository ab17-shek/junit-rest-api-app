package com.book.junit_rest_api_app.controller;

import com.book.junit_rest_api_app.entity.Book;
import com.book.junit_rest_api_app.service.impl.BookServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(value = "/book")
public class BookController {

    Logger LOGGER = LogManager.getLogger(BookController.class);

    @Autowired
    private BookServiceImpl bookService;

    @PostMapping("/add")
    ResponseEntity<Book> addBook(@RequestBody Book book) {
        LOGGER.info("Inside addBook : {}");
        Book result = bookService.saveBook(book);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @GetMapping("/getBooks")
        //if in request good rating comes as true
        //  -> we will filter book records whose rating is equal and greater than 8
    ResponseEntity<List<Book>> getBooks(@RequestHeader boolean goodRating) {
        LOGGER.info("Inside getBooks : ");
        List<Book> books = bookService.getAllBooks(goodRating);
        LOGGER.info("Books : " + books);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @GetMapping("/getById/{id}")
    ResponseEntity<Book> getBookById(@PathVariable(name = "id") Long bookId) {
        LOGGER.info("Inside getBookById : ");
        Optional<Book> book = bookService.getBookById(bookId);
        return book.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/deletetById/{id}")
    ResponseEntity<String> deleteBookById(@PathVariable(name = "id") Long bookId) {
        LOGGER.info("Inside deleteBookById : ");
        Optional<Book> book = bookService.getBookById(bookId);
        if (book.isPresent()) {
            bookService.deleteBookById(bookId);
            return new ResponseEntity<>("Book details deleted for id : " + bookId, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/updateById/{id}")
    ResponseEntity<Map<String, Book>> updateBookById(@PathVariable(name = "id") Long bookId, @RequestBody Book book) {
        LOGGER.info("Inside UpdateBookById : ");
        Optional<Book> bookDetails = bookService.getBookById(bookId);
        if (bookDetails.isPresent()) {
            Book updatedBook = bookService.updateBookById(bookId, book);
            Map<String, Book> bookMap = new HashMap<>();
            bookMap.put("Book details updated for id : " + bookId, updatedBook);
            return new ResponseEntity<>(bookMap, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
