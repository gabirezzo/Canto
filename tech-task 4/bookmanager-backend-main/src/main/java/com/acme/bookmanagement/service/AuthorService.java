package com.acme.bookmanagement.service;

import com.acme.bookmanagement.model.Author;
import com.acme.bookmanagement.repository.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {
    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public List<Author> findAll() {
        return authorRepository.findAll();
    }

    public Author create(String name) {
        Author author = new Author(1L, name);
        return authorRepository.save(author);
    }

    public Author findById(Long id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Author not found with id: " + id));
    }

    public Author update(Long id, Author authorDetails) {
        Author author = findById(id);
        author.setName(authorDetails.getName());
        return authorRepository.save(author);
    }

    public void delete(Long id) {
        Author author = findById(id);
        authorRepository.delete(author);
    }
}