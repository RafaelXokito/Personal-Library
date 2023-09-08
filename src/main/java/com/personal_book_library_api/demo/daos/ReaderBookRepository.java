package com.personal_book_library_api.demo.daos;

import com.personal_book_library_api.demo.entities.Book;
import com.personal_book_library_api.demo.entities.Reader;
import com.personal_book_library_api.demo.entities.ReaderBook;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReaderBookRepository extends JpaRepository<ReaderBook, Long> {
    Optional<ReaderBook> findByReaderAndBook(Reader reader, Book book);
}
