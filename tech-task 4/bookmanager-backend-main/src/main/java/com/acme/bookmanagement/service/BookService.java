package com.acme.bookmanagement.service;

import com.acme.bookmanagement.model.Book;
import com.acme.bookmanagement.model.Author;
import com.acme.bookmanagement.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.time.LocalDate;
import java.util.Optional;

@Service
public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public Book create(String name, List<Author> authors, LocalDate publishedDate) {
        Book book = new Book(0L, name, authors, publishedDate);
        return bookRepository.save(book);
    }

    public Book findById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + id));
    }
    public Book update(Long id, Book bookDetails) {
        Book book = findById(id);
        book.setTitle(bookDetails.getTitle());
        book.setAuthors(bookDetails.getAuthors());
        book.setPublishedDate(bookDetails.getPublishedDate());
        return bookRepository.save(book);
    }

    public void delete(Long id) {
        Book book = findById(id);
        bookRepository.delete(book);
    }

    public List<Book> findByPublishedDate(LocalDate publishedDate) {
        return bookRepository.findByPublishedDate(publishedDate);
    }
}

