package com.personal_book_library_api.demo.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ReaderDTO {

    private Long id;
    private String idCard;
    private int fontSize;
    private BookDTO currentBooks;
    private List<BookDTO> books;
    private String firstName;
    private String lastName;
    private boolean active;
    private String email;
}
