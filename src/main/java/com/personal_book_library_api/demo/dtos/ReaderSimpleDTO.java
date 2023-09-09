package com.personal_book_library_api.demo.dtos;

import com.personal_book_library_api.demo.entities.Reader;
import com.personal_book_library_api.demo.entities.ReaderBook;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Builder
@Data
public class ReaderSimpleDTO {
    private String name;
    private String email;

    public static ReaderSimpleDTO from(Reader reader) {
        return builder()
            .name(reader.getFirstName() + " " + reader.getLastName())
            .email(reader.getEmail())
            .build();
    }

    public static List<ReaderSimpleDTO> from(List<Reader> readers) {
        return readers.stream()
            .map(ReaderSimpleDTO::from)
            .collect(java.util.stream.Collectors.toList());
    }

    public static List<ReaderSimpleDTO> fromReaderBooks(List<ReaderBook> readerBooks) {
        return readerBooks.stream()
                .map(readerBook -> ReaderSimpleDTO.from(readerBook.getReader()))
                .collect(Collectors.toList());
    }
}
