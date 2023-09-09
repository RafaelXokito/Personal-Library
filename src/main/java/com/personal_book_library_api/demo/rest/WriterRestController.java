package com.personal_book_library_api.demo.rest;

import com.personal_book_library_api.demo.dtos.WriterDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.personal_book_library_api.demo.entities.Writer;
import com.personal_book_library_api.demo.services.WriterService;

@RestController
@RequestMapping("/api")
public class WriterRestController {

    private WriterService writerService;

    public WriterRestController() {
    }

    @Autowired
    public WriterRestController(WriterService writerService) {
        this.writerService = writerService;
    }

    @PostMapping("/writers")
    @Deprecated
    public ResponseEntity<WriterDTO> save(@RequestBody Writer writer) {
        writerService.save(writer);
        return ResponseEntity.ok(WriterDTO.from(writer));
    }
    
}