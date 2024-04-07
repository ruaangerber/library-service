package com.awesome.library.service.repository;

import java.util.List;

import org.springframework.data.couchbase.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.awesome.library.service.domain.model.Book;


public interface BookRepository extends CrudRepository<Book, String> {
    
    Book findByIsbn(String isbn);

    @Query("SELECT library.`_class`, META(`library`).`id` AS __id, library.`isbn`, library.`title`, library.`description`, library.`authors`, library.`publishYear`, library.`created`, library.`updated` FROM `library` UNNEST library.authors AS author WHERE library.isbn LIKE '%'||$1||'%' OR library.title LIKE '%'||$1||'%' OR library.description LIKE '%'||$1||'%' OR author.name LIKE '%'||$1||'%' OR library.publishYear = TONUMBER($1)")
    List<Book> findByAny(String search);

}
