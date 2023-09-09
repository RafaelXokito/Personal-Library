package com.personal_book_library_api.demo.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class BookDTO {

    private Long id;
    private String title;
    private String description;
    private String content;
    private List<ReaderDTO> currentReaders;
    private List<ReaderDTO> readers;
    private WriterDTO writer;
}
