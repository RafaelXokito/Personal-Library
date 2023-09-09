package com.personal_book_library_api.demo.dtos;

import com.personal_book_library_api.demo.entities.Writer;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class WriterSimpleDTO {
    private String name;
    private String email;

    public static WriterSimpleDTO from(Writer writer) {
        return builder()
            .name(writer.getFirstName() + " " + writer.getLastName())
            .email(writer.getEmail())
            .build();
    }

    public static List<WriterSimpleDTO> from(List<Writer> writers) {
        return writers.stream()
            .map(WriterSimpleDTO::from)
            .collect(java.util.stream.Collectors.toList());
    }
}
