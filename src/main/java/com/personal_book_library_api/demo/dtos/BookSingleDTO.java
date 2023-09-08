package com.personal_book_library_api.demo.dtos;

import java.util.List;

public class BookSingleDTO {

    private Long id;
    private String title;
    private String description;
    private String content;
    private WriterDTO writer;

    public BookSingleDTO() {
    }

    public BookSingleDTO(Long id, String title, String description, String content, WriterDTO writer) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.content = content;
        this.writer = writer;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public WriterDTO getWriter() {
        return writer;
    }

    public void setWriter(WriterDTO writer) {
        this.writer = writer;
    }
}
