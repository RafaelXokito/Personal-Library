package com.personal_book_library_api.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.personal_book_library_api.demo.dtos.WriterRepository;
import com.personal_book_library_api.demo.entities.Writer;

import jakarta.transaction.Transactional;

@Service
public class WriterService {
    
    private WriterRepository writerRepository;

    public WriterService() {
    }

    @Autowired
    public WriterService(WriterRepository writerRepository) {
        this.writerRepository = writerRepository;
    }

    @Transactional
    public Writer save(Writer writer) {
        writerRepository.save(writer);
        System.out.println(writer.toString());
        return writer;
    }
}
