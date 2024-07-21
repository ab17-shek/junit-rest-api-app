package com.book.junit_rest_api_app.service;

import com.book.junit_rest_api_app.entity.Book;

import java.util.List;

public interface BookService {
    Book saveBook(Book book);

    List<Book> getAllBooks(boolean goodRating);
}
