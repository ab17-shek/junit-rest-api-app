package com.book.junit_rest_api_app.service;

import com.book.junit_rest_api_app.entity.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {
    Book saveBook(Book book);

    List<Book> getAllBooks(boolean goodRating);

    Optional<Book> getBookById(Long bookId);

    void deleteBookById(Long bookId);

    Book updateBookById(Long bookId, Book book);
}
