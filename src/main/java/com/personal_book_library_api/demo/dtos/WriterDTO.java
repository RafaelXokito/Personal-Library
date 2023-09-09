package com.personal_book_library_api.demo.dtos;

import com.personal_book_library_api.demo.entities.Book;
import com.personal_book_library_api.demo.entities.Writer;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Builder
@Data
public class WriterDTO {

    private Long id;
    private String idCard;
    private List<BookDTO> books;
    private String firstName;
    private String lastName;
    private boolean active;
    private String email;

    public static WriterDTO from(Writer writer) {
            return builder()
                    .id(writer.getId())
                    .idCard(writer.getIdCard())
                    .books(BookDTO.from(writer.getBooks()))
                    .firstName(writer.getFirstName())
                    .lastName(writer.getLastName())
                    .active(writer.isActive())
                    .email(writer.getEmail())
                    .build();
    }

    public static List<WriterDTO> from(List<Writer> writers) {
        return writers.stream()
                .map(WriterDTO::from)
                .collect(Collectors.toList());
    }
}
