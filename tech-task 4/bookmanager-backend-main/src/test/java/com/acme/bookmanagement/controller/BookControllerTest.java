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

    @MockBean
    private AuthorController authorController;

    Author authorSave = new Author(1L, "author-1");
    Author authorSave2 = new Author(2L, "author-2");


    private final Map<Long, Book> books = Map.of(
            1L, new Book(1L,
                    "title-1",
                new ArrayList<>(Collections.singletonList(authorSave)),
                    LocalDate.of(2021, 2, 3)),
            2L, new Book(2L,
                    "title-2",
                    new ArrayList<>(Collections.singletonList(authorSave2)),
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
                            "id": "1",
                            "title": "title-1",
                            "authors": [{"name": "author-1"}],
                            "publishedDate": "2021-02-03"
                        },
                        {
                            "id": "2",
                            "title": "title-2",
                            "authors": [{"name": "author-2" }],
                            "publishedDate": "2021-02-03"
                        }
                    ]
                """);
    }

    @Test
    void shouldGetBookById() {
        when(this.bookService.findById(1L))
                .thenReturn(books.get(1L));

        this.graphQlTester
                .documentName("findBookById")
                .variable("id", 1L)
                .execute()
                .path("findBookById")
                .matchesJson("""
                    {
                        "id": "1",
                        "title": "title-1",
                        "authors": [{"name": "author-1"}],
                        "publishedDate": "2021-02-03"
                    }
                """);
    }

    @Test
    void shouldCreateBook() {
        when(this.authorController.createAuthors(any()))
                .thenReturn(new ArrayList<>(Arrays.asList(authorSave, authorSave2)));

        // Return a Book with BOTH authors
        Book createdBook = new Book(1L, "title-1", new ArrayList<>(Arrays.asList(authorSave, authorSave2)), LocalDate.of(2021, 2, 3));
        when(this.bookService.create("title-1", 
                new ArrayList<>(Arrays.asList(authorSave, authorSave2)), 
                LocalDate.of(2021, 2, 3)))
                .thenReturn(createdBook);

        this.graphQlTester
                .documentName("createBook")
                .variable("title", "title-1")
                .variable("authors", Arrays.asList("author-1", "author-2"))
                .variable("publishedDate", "2021-02-03")
                .execute()
                .path("createBook")
                .matchesJson("""
                    {
                        "id": "1",
                        "title": "title-1",
                        "authors": [{"name": "author-1"}, {"name": "author-2"}],
                        "publishedDate": "2021-02-03"
                    }
                """);
    }

    @Test
    void shouldUpdateBook() {
        Book updatedBook = new Book(1L, "updated-title", 
                new ArrayList<>(Collections.singletonList(authorSave)), 
                LocalDate.of(2021, 2, 3));

        when(this.bookService.update(1L, updatedBook))
                .thenReturn(updatedBook);

        this.graphQlTester
                .documentName("updateBook")
                .variable("id", 1L)
                .variable("bookDetails", updatedBook)
                .execute()
                .path("updateBook")
                .matchesJson("""
                    {
                        "id": "1",
                        "title": "updated-title",
                        "authors": [{"name": "author-1"}],
                        "publishedDate": "2021-02-03"
                    }
                """);
    }

    @Test
    void shouldDeleteBook() {
        when(this.bookService.delete(1L))
                .thenReturn(true);

        this.graphQlTester
                .documentName("deleteBook")
                .variable("id", 1L)
                .execute()
                .path("deleteBook")
                .entity(boolean.class)
                .isEqualTo(true);
    }

    @Test
    void shouldFilterBooksByDate() {
        LocalDate date = LocalDate.of(2021, 2, 3);
        List<Book> filteredBooks = new ArrayList<>(Collections.singletonList(books.get(1L)));

        when(this.bookService.findByPublishedDate(date))
                .thenReturn(filteredBooks);

        this.graphQlTester
                .documentName("filterBooksByDate")
                .variable("publishedDate", date)
                .execute()
                .path("filterBooksByDate")
                .matchesJson("""
                    [
                        {
                            "id": "1",
                            "title": "title-1",
                            "authors": [{"name": "author-1"}],
                            "publishedDate": "2021-02-03"
                        }
                    ]
                """);
    }
}