package com.personal_book_library_api.demo.dtos;

import com.personal_book_library_api.demo.entities.Book;
import lombok.*;

import java.util.List;

@Builder
@Data
public class BookSimpleDTO {

    private Long id;
    private String title;
    private String description;
    private String writerName;

    public static BookSimpleDTO from(Book book) {
        return BookSimpleDTO.builder()
            .id(book.getId())
            .title(book.getTitle())
            .description(book.getDescription())
            .writerName(book.getWriter().getFirstName() + " " + book.getWriter().getLastName())
            .build();
    }

    public static List<BookSimpleDTO> from(List<Book> books) {
        return books.stream()
            .map(BookSimpleDTO::from)
            .collect(java.util.stream.Collectors.toList());
    }
}
