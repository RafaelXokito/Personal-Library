package com.personal_book_library_api.demo.dtos;

import com.personal_book_library_api.demo.entities.Book;
import com.personal_book_library_api.demo.entities.Reader;
import com.personal_book_library_api.demo.entities.ReaderBook;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Builder
@Data
public class BookDTO {

    private Long id;
    private String title;
    private String description;
    private String content;
    private List<ReaderDTO> currentReaders;
    private List<ReaderDTO> readers;
    private WriterDTO writer;

    public static BookDTO from(Book book) {
            return builder()
                    .id(book.getId())
                    .title(book.getTitle())
                    .description(book.getDescription())
                    .content(book.getContent())
                    .currentReaders(ReaderDTO.from(book.getCurrentReaders()))
                    .readers(ReaderDTO.fromReaderBooks(book.getReaders()))
                    .writer(WriterDTO.from(book.getWriter()))
                    .build();
    }

    public static List<BookDTO> from(List<Book> books) {
        return books.stream()
                .map(BookDTO::from)
                .collect(Collectors.toList());
    }

    public static List<BookDTO> fromReaderBooks(List<ReaderBook> readerBooks) {
        return readerBooks.stream()
                .map(readerBook -> BookDTO.from(readerBook.getBook()))
                .collect(Collectors.toList());
    }

}
