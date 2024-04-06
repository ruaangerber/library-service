package com.awesome.library.service.repository;

import org.springframework.data.repository.CrudRepository;

import com.awesome.library.service.domain.model.Book;


public interface BookRepository extends CrudRepository<Book, String> {
    
    Book findByIsbn(String isbn);

}
