package com.acme.bookmanagement.repository;

import com.acme.bookmanagement.model.Book;
import com.acme.bookmanagement.model.Author;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(properties = {
        "spring.datasource.url=jdbc:h2:mem:testdb",
        "spring.jpa.hibernate.ddl-auto=create-drop"
})
public class BookRepositoryTest {

    @Autowired
    private BookRepository repo;

    private AuthorRepository authorRepository;


    private Book book;

    @BeforeEach
    public void setUp() {
        Author author = new Author(1L, "author-1");
        author = authorRepository.save(author);
        book = new Book(null,
                "title-1",
                Collections.singletonList(author),
                LocalDate.of(2021, 2, 3));
        book = repo.save(book);
    }

    @AfterEach
    public void tearDown() {
        repo.delete(book);
    }

    @Test
    void testSavedBookCanBeFoundById() {
        Book savedBook = repo.findById(book.getId()).orElse(null);

        assertNotNull(savedBook);
        assertEquals(book.getAuthors(), savedBook.getAuthors());
        assertEquals(book.getTitle(), savedBook.getTitle());
        assertEquals(book.getPublishedDate(), savedBook.getPublishedDate());
    }

    
    @Test
    void findByPublishedDate_returnsBooksWithMatchingDate() {
        LocalDate date = LocalDate.of(2024, 6, 19);
        book.setPublishedDate(date);
        repo.save(book);

        List<Book> books = repo.findByPublishedDate(date);

        assertThat(books).isNotEmpty();
        assertThat(books.get(0).getTitle()).isEqualTo("title-1");
    }

    @Test
    void findAllBooks() {
        List<Book> books = repo.findAll();
        assertNotNull(books);
        assertEquals(1, books.size());
        assertEquals(book.getTitle(), books.get(0).getTitle());
    }

    @Test
    void testDeleteBook() {
        repo.delete(book);
        Book deletedBook = repo.findById(book.getId()).orElse(null);
        assertEquals(null, deletedBook);
        assertEquals(0, repo.count());
    }
    
    @Test
    void testUpdateBook() {
        book.setTitle("Updated Title");
        book.setPublishedDate(LocalDate.of(2022, 1, 1));
        Book updatedBook = repo.save(book);
        assertNotNull(updatedBook);
        assertEquals("Updated Title", updatedBook.getTitle());
        assertEquals(LocalDate.of(2022, 1, 1), updatedBook.getPublishedDate());
    }

}
