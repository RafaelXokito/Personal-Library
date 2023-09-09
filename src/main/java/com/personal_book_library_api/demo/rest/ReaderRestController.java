package com.personal_book_library_api.demo.rest;

import com.personal_book_library_api.demo.dtos.ReaderDTO;
import com.personal_book_library_api.demo.entities.Reader;
import com.personal_book_library_api.demo.services.ReaderService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@NoArgsConstructor
@RequestMapping("/api")
public class ReaderRestController {

    private ReaderService readerService;

    @Autowired
    public ReaderRestController(ReaderService readerService) {
        this.readerService = readerService;
    }

    @PostMapping("/readers")
    @Deprecated
    public ResponseEntity<ReaderDTO> save(@RequestBody Reader reader) {
        readerService.save(reader);
        return ResponseEntity.ok(ReaderDTO.from(reader));
    }
}