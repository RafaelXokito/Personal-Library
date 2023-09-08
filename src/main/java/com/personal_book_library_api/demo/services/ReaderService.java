package com.personal_book_library_api.demo.services;

import com.personal_book_library_api.demo.daos.ReaderRepository;
import com.personal_book_library_api.demo.entities.Reader;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ReaderService {
    
    private ReaderRepository readerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public ReaderService() {
    }

    @Autowired
    public ReaderService(ReaderRepository readerRepository) {
        this.readerRepository = readerRepository;
    }

    @Transactional
    public void save(Reader reader) {
        reader.setPassword(passwordEncoder.encode(reader.getPassword()));
        readerRepository.save(reader);
        System.out.println(reader.toString());
    }
}
