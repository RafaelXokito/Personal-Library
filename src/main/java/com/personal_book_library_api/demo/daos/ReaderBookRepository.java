package com.personal_book_library_api.demo.daos;

import com.personal_book_library_api.demo.entities.Book;
import com.personal_book_library_api.demo.entities.Reader;
import com.personal_book_library_api.demo.entities.ReaderBook;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReaderBookRepository extends JpaRepository<ReaderBook, Long> {
    List<ReaderBook> findByBook_Id(Long id);
    Optional<ReaderBook> findByReaderAndBook(Reader reader, Book book);
}
