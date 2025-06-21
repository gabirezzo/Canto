package com.acme.bookmanagement.service;

import com.acme.bookmanagement.model.Book;
import com.acme.bookmanagement.model.Author;
import com.acme.bookmanagement.repository.BookRepository;
import com.acme.bookmanagement.repository.AuthorRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BookServiceTest {
    private final BookRepository bookRepository = Mockito.mock(BookRepository.class);
    private final BookService bookService = new BookService(bookRepository);
    private final AuthorRepository authorRepository = Mockito.mock(AuthorRepository.class);

    Author author = new Author(1L, "author-1");
    Author authorSave = authorRepository.save(author);

    private final Book book = new Book(1L,
            "title-1",
            Collections.singletonList(authorSave),
            LocalDate.of(2021, 2, 3));

    @Test
    void testFindAll() {
        Mockito.when(bookRepository.findAll()).thenReturn(Collections.singletonList(book));
        assertEquals(1, bookService.findAll().size());
    }
}
