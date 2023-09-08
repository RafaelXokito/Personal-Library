package com.personal_book_library_api.demo.daos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.personal_book_library_api.demo.entities.Book;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    @Query("SELECT b FROM Book b WHERE " +
            "LOWER(b.title) LIKE LOWER(CONCAT('%', :title, '%')) AND " +
            "(LOWER(b.writer.firstName) LIKE LOWER(CONCAT('%', :writerName, '%')) OR " +
            "LOWER(b.writer.lastName) LIKE LOWER(CONCAT('%', :writerName, '%'))) AND " +
            "(LOWER(b.description) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    List<Book> search(@Param("title") String title, @Param("keyword") String keyword, @Param("writerName") String writerName);

}
