package com.personal_book_library_api.demo.dtos;

import com.personal_book_library_api.demo.entities.ReaderBook;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Builder
@Data
public class ReaderBookDTO {
    private Long id;
    private ReaderSimpleDTO reader;
    private BookSimpleDTO book;
    private int currentPage;

    public static ReaderBookDTO from(ReaderBook readerBook) {
        return builder()
            .id(readerBook.getId())
            .reader(ReaderSimpleDTO.from(readerBook.getReader()))
            .book(BookSimpleDTO.from(readerBook.getBook()))
            .currentPage(readerBook.getCurrentPage())
            .build();
    }

    public static List<ReaderBookDTO> from(List<ReaderBook> readerBooks) {
        return readerBooks.stream()
            .map(ReaderBookDTO::from)
            .collect(Collectors.toList());
    }
}
