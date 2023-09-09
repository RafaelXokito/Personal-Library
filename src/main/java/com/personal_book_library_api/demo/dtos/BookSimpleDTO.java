package com.personal_book_library_api.demo.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class BookSimpleDTO {

    private Long id;
    private String title;
    private String description;
    private String writerName;
}
