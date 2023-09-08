package com.personal_book_library_api.demo.daos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.personal_book_library_api.demo.entities.Writer;

import java.util.Optional;

public interface WriterRepository extends JpaRepository<Writer, Long> {
    Optional<Writer> findByEmail(String email);
    
}
