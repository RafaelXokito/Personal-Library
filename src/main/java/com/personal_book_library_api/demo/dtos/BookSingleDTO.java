package com.personal_book_library_api.demo.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class BookSingleDTO {

    private Long id;
    private String title;
    private String description;
    private String content;
    private WriterDTO writer;

}
