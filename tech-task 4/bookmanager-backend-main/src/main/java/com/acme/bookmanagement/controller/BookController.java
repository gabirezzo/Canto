package com.acme.bookmanagement.controller;

import com.acme.bookmanagement.model.Book;
import com.acme.bookmanagement.model.Author;
import com.acme.bookmanagement.service.BookService;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;
import java.time.LocalDate;

@Controller
@RequestMapping("/graphql")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @QueryMapping
    public List<Book> findAllBooks() {
        return bookService.findAll();
    }

    @QueryMapping
    public Book findBookById(Long id) {
        return bookService.findById(id);
    }

    @QueryMapping
    public Book createBook(String title, Author author, LocalDate publishedDate) {
        return bookService.create(title, author, publishedDate);
    }

    @QueryMapping
    public Book updateBook(Long id, Book bookDetails) {
        return bookService.update(id, bookDetails);
    }

    @QueryMapping
    public void deleteBook(Long id) {
        bookService.delete(id);
    }

    @QueryMapping
    public List<Book> filterBooksByDate(LocalDate publishedDate) {
        return bookService.findByPublishedDate(publishedDate);
    }

}
