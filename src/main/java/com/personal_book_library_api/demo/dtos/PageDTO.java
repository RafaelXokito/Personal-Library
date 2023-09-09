package com.personal_book_library_api.demo.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PageDTO {
    private int page;
    private int size;
    private String content;

    public PageDTO(int page, int size, String content) {
        this.page = page;
        this.size = size;
        this.content = content;
    }
}
