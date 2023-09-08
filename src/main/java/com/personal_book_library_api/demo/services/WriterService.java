package com.personal_book_library_api.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.personal_book_library_api.demo.daos.WriterRepository;
import com.personal_book_library_api.demo.entities.Writer;

import jakarta.transaction.Transactional;

@Service
public class WriterService {
    
    private WriterRepository writerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public WriterService() {
    }

    @Autowired
    public WriterService(WriterRepository writerRepository) {
        this.writerRepository = writerRepository;
    }

    @Transactional
    public void save(Writer writer) {
        writer.setPassword(passwordEncoder.encode(writer.getPassword()));
        writerRepository.save(writer);
        System.out.println(writer.toString());
    }

    public Writer getWriter(String username) {
        return writerRepository.findByEmail(username).orElseThrow();
    }
}
