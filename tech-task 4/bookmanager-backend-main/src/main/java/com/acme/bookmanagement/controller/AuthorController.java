package com.acme.bookmanagement.controller;

import com.acme.bookmanagement.model.Author;
import com.acme.bookmanagement.service.AuthorService;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/graphql")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @QueryMapping
    public List<Author> findAllAuthors() {
        return authorService.findAll();
    }

    @QueryMapping
    public Author findAuthorById(Long id) {
        return authorService.findById(id);
    }

    @QueryMapping
    public Author createAuthor(String name) {
        return authorService.create(name);
    }

    @QueryMapping
    public List<Author> createAuthors(List<String> names) {
        return names.stream()
                .map(authorService::create)
                .toList();
}

    @QueryMapping
    public Author updateAuthor(Long id, Author authorDetails) {
        return authorService.update(id, authorDetails);
    }

    @QueryMapping
    public void deleteAuthor(Long id) {
        authorService.delete(id);
    }
}