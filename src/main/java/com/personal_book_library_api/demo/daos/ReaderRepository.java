package com.personal_book_library_api.demo.daos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.personal_book_library_api.demo.entities.Reader;

import java.util.Optional;

public interface ReaderRepository extends JpaRepository<Reader, Long>{
    Optional<Reader> findByEmail(String email);
    
}
