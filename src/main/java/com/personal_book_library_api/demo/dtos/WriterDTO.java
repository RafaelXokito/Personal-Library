package com.personal_book_library_api.demo.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class WriterDTO {

    private Long id;
    private String idCard;
    private List<BookDTO> books;
    private String firstName;
    private String lastName;
    private boolean active;
    private String email;

}
