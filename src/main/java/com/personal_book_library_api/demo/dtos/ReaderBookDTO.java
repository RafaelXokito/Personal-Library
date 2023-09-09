package com.personal_book_library_api.demo.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReaderBookDTO {
    private Long id;
    private ReaderDTO reader;
    private BookSimpleDTO book;
    private int currentPage;
}
