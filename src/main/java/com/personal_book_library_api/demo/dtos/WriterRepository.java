package com.personal_book_library_api.demo.dtos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.personal_book_library_api.demo.entities.Writer;

public interface WriterRepository extends JpaRepository<Writer, Long> {
    
}
