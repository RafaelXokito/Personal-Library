package com.personal_book_library_api.demo.dtos;

import com.personal_book_library_api.demo.entities.Reader;
import com.personal_book_library_api.demo.entities.ReaderBook;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Builder
@Data
public class ReaderDTO {

    private Long id;
    private String idCard;
    private int fontSize;
    private BookDTO currentBook;
    private List<BookDTO> books;
    private String firstName;
    private String lastName;
    private boolean active;
    private String email;

    public static ReaderDTO from(Reader reader) {
            return builder()
                    .id(reader.getId())
                    .idCard(reader.getIdCard())
                    .fontSize(reader.getFontSize())
                    .currentBook(BookDTO.from(reader.getCurrentBook()))
                    .books(BookDTO.fromReaderBooks(reader.getBooks()))
                    .firstName(reader.getFirstName())
                    .lastName(reader.getLastName())
                    .active(reader.isActive())
                    .email(reader.getEmail())
                    .build();
    }

    public static List<ReaderDTO> from(List<Reader> readers) {
        return readers.stream()
                .map(ReaderDTO::from)
                .collect(Collectors.toList());
    }

    public static List<ReaderDTO> fromReaderBooks(List<ReaderBook> readerBooks) {
        return readerBooks.stream()
                .map(readerBook -> ReaderDTO.from(readerBook.getReader()))
                .collect(Collectors.toList());
    }

}
