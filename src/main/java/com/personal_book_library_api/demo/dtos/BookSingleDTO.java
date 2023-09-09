package com.personal_book_library_api.demo.dtos;

import com.personal_book_library_api.demo.entities.Book;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Builder
@Data
public class BookSingleDTO {

    private Long id;
    private String title;
    private String description;
    private String content;
    private WriterSimpleDTO writer;

    public static BookSingleDTO from(Book book) {
        return builder()
            .id(book.getId())
            .title(book.getTitle())
            .description(book.getDescription())
            .content(book.getContent())
            .writer(WriterSimpleDTO.from(book.getWriter()))
            .build();
    }

    public static List<BookSingleDTO> from(List<Book> books) {
        return books.stream()
            .map(BookSingleDTO::from)
            .collect(Collectors.toList());
    }

}
