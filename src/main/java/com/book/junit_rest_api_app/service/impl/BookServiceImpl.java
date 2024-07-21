package com.book.junit_rest_api_app.service.impl;

import com.book.junit_rest_api_app.entity.Book;
import com.book.junit_rest_api_app.exception.BookNotFoundException;
import com.book.junit_rest_api_app.repository.BookRepository;
import com.book.junit_rest_api_app.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public List<Book> getAllBooks(boolean goodRating) {
        List<Book> bookList = bookRepository.findAll();
        List<Book> filtered = bookList.stream().filter(book -> book.getRating() >= 8)
                .collect(Collectors.toList());
        return goodRating ? filtered : bookList;
        // return Collections.unmodifiableList(bookRepository.findAll());
    }

    @Override
    public Optional<Book> getBookById(Long bookId) {
        return Optional.ofNullable(bookRepository.findById(bookId).orElseThrow(() -> new BookNotFoundException("Book details not found for id : " + bookId)));
    }

    @Override
    public void deleteBookById(Long bookId) {
        bookRepository.deleteById(bookId);
    }

    @Override
    public Book updateBookById(Long bookId, Book book) {
        Optional<Book> bookDetails = bookRepository.findById(bookId);
        bookDetails.get().setBookName(book.getBookName());
        bookDetails.get().setRating(book.getRating());
        return bookRepository.save(bookDetails.get());
    }
}
