package com.personal_book_library_api.demo.dtos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.personal_book_library_api.demo.entities.Reader;

public interface ReaderRepository extends JpaRepository<Reader, Long>{
    
}
