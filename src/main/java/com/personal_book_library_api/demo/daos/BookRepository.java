package com.personal_book_library_api.demo.daos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.personal_book_library_api.demo.entities.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

}
