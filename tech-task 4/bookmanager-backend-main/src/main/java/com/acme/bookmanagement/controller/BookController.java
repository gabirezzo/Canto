package com.acme.bookmanagement.controller;

import com.acme.bookmanagement.model.Book;
import com.acme.bookmanagement.model.Author;
import com.acme.bookmanagement.service.BookService;
import com.acme.bookmanagement.controller.AuthorController;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;
import java.time.LocalDate;

@Controller
@RequestMapping("/graphql")
public class BookController {

    private final BookService bookService;
    private final AuthorController authorController;

    public BookController(BookService bookService, AuthorController authorController) {
        this.bookService = bookService; 
        this.authorController = authorController;
    }

    @QueryMapping
    public List<Book> findAllBooks() {
        return bookService.findAll();
    }

    @QueryMapping
    public Book findBookById(Long id) {
        return bookService.findById(id);
    }

    @MutationMapping
    public Book createBook(String title, List<String> authors, LocalDate publishedDate) {
        List<Author> modelAuthors = authorController.createAuthors(authors);
        return bookService.create(title, modelAuthors, publishedDate);
    }

    @MutationMapping
    public Book updateBook(Long id, Book bookDetails) {
        return bookService.update(id, bookDetails);
    }

    @MutationMapping
    public boolean deleteBook(@Argument Long id) {
        return bookService.delete(id);
}

    @QueryMapping
    public List<Book> filterBooksByDate(LocalDate publishedDate) {
        return bookService.findByPublishedDate(publishedDate);
    }

}
