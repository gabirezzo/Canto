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

    @Test
    void testCreate() {
        Mockito.when(bookRepository.save(Mockito.any(Book.class))).thenReturn(book);
        Book createdBook = bookService.create("title-1", Collections.singletonList(authorSave), LocalDate.of(2021, 2, 3));
        assertEquals("title-1", createdBook.getTitle());
        assertEquals(1, createdBook.getAuthors().size());
    }

    @Test
    void testFindById() {
        Mockito.when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        Book foundBook = bookService.findById(1L);
        assertEquals("title-1", foundBook.getTitle());
        assertEquals(1, foundBook.getAuthors().size());
    }

    @Test
    void testUpdate() {
        Book updatedBook = new Book(1L, "updated-title", Collections.singletonList(authorSave), LocalDate.of(2021, 2, 3));
        Mockito.when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        Mockito.when(bookRepository.save(Mockito.any(Book.class))).thenReturn(updatedBook);
        
        Book result = bookService.update(1L, updatedBook);
        assertEquals("updated-title", result.getTitle());
    }

    @Test   
    void testDelete() {
        Mockito.when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        Mockito.doNothing().when(bookRepository).delete(Mockito.any(Book.class));
        
        boolean isDeleted = bookService.delete(1L);
        assertEquals(true, isDeleted);
        
        Mockito.verify(bookRepository, Mockito.times(1)).delete(Mockito.any(Book.class));
    }

    @Test
    void testFindByPublishedDate() {
        LocalDate date = LocalDate.of(2021, 2, 3);
        Mockito.when(bookRepository.findByPublishedDate(date)).thenReturn(Collections.singletonList(book));
        
        assertEquals(1, bookService.findByPublishedDate(date).size());
        assertEquals("title-1", bookService.findByPublishedDate(date).get(0).getTitle());
    }

}
