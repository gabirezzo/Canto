package com.acme.bookmanagement.controller;

import com.acme.bookmanagement.model.Book;
import com.acme.bookmanagement.model.Author;
import com.acme.bookmanagement.service.BookService;
import com.acme.bookmanagement.repository.AuthorRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest;
import org.springframework.graphql.test.tester.GraphQlTester;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@GraphQlTest(BookController.class)
public class BookControllerTest {

    @Autowired
    private GraphQlTester graphQlTester;

    @MockBean
    private BookService bookService;
    private AuthorRepository authorRepository;

    Author author = new Author(1L, "author-1");
    Author authorSave = authorRepository.save(author);
    Author author2 = new Author(1L, "author-2");
    Author authorSave2 = authorRepository.save(author2);


    private final Map<Long, Book> books = Map.of(
            1L, new Book(1L,
                    "title-1",
                    Collections.singletonList(authorSave),
                    LocalDate.of(2021, 2, 3)),
            2L, new Book(2L,
                    "title-2",
                    Collections.singletonList(authorSave2),
                    LocalDate.of(2021, 2, 3))
    );

    @Test
    void shouldGetAllBooks() {
        List<Book> allBooks = books.values().stream()
                .sorted(Comparator.comparing(Book::getId))
                .toList();
        when(this.bookService.findAll())
                .thenReturn(new ArrayList<>(books.values()));

        this.graphQlTester
                .documentName("findAllBooks")
                .execute()
                .path("findAllBooks")
                .matchesJson("""
                    [
                        {
                            "id": 1,
                            "title": "title-1",
                            "authors": {"name: "author-1" },
                            "publishedDate": "2021-02-03"
                        },
                        {
                            "id": 2,
                            "title": "title-2",
                            "authors": {"name: "author-2" },
                            "publishedDate": "2021-02-03"
                        }
                    ]
                """);
    }
}