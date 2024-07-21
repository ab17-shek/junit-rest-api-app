package com.book.junit_rest_api_app.controller;

import com.book.junit_rest_api_app.entity.Book;
import com.book.junit_rest_api_app.service.impl.BookServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
//@MockitoSettings(strictness = Strictness.LENIENT) is the simplest way to adjust strictness in my setup
@MockitoSettings(strictness = Strictness.LENIENT)
public class BookControllerTest {

    Book book1 = new Book(1L, "Ikigai", 10);
    Book book2 = new Book(2L, "Money", 7);
    Book book3 = new Book(3L, "Man Matters", 10);
    private MockMvc mockMvc;
    @Mock
    private BookServiceImpl bookService;
    @InjectMocks
    private BookController bookController;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
    }

    @Test
    public void createBookAPISuccess() throws Exception {
        Book book = new Book(1L, "Happy Life", 10);
        System.out.println(asJsonString(book));

        mockMvc.perform(MockMvcRequestBuilders.post("/book/add")
                        .content(asJsonString(book))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void getBooksAPI() throws Exception {
        List<Book> bookList = Arrays.asList(book1, book2, book3);
        boolean goodRating = false;
        List<Book> filteredBookList = Arrays.asList(book1, book3);
        //when bookService get all books method is called return bookList
        if (goodRating)
            when(bookService.getAllBooks(goodRating)).thenReturn(filteredBookList);
        else
            when(bookService.getAllBooks(goodRating)).thenReturn(bookList);

        mockMvc.perform(MockMvcRequestBuilders.get("/book/getBooks").header("goodRating", goodRating)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].bookName").value("Ikigai"));
    }

    @Test
    public void getBooksSuccess() {
        List<Book> bookList = Arrays.asList(book1, book2, book3);

        boolean goodRating = true;

        //when bookService get all books method is called return bookList
        when(bookService.getAllBooks(goodRating)).thenReturn(bookList);

        List<Book> books = bookService.getAllBooks(goodRating);

        Assertions.assertEquals(3, books.size());

        //verifies how many times the method has been called
        verify(bookService, times(1)).getAllBooks(goodRating);
    }

    public static String asJsonString(final Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }
}

