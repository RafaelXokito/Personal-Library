package com.personal_book_library_api.demo.dtos;

import java.util.List;

public class BookSimpleDTO {

    private Long id;
    private String title;
    private String description;
    private String writerName;

    public BookSimpleDTO() {
    }

    public BookSimpleDTO(Long id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public BookSimpleDTO(Long id, String title, String description, String writerName) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.writerName = writerName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWriterName() {
        return writerName;
    }

    public void setWriterName(String writerName) {
        this.writerName = writerName;
    }
}
