package com.personal_book_library_api.demo.dtos;

import java.util.List;

public class BookDTO {

    private Long id;
    private String title;
    private String description;
    private String content;
    private List<ReaderDTO> currentReaders;
    private List<ReaderDTO> readers;
    private WriterDTO writer;

    public BookDTO() {
    }

    public BookDTO(Long id, String title, String description, String content, List<ReaderDTO> currentReaders, List<ReaderDTO> readers, WriterDTO writer) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.content = content;
        this.currentReaders = currentReaders;
        this.readers = readers;
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

    public List<ReaderDTO> getCurrentReaders() {
        return currentReaders;
    }

    public void setCurrentReaders(List<ReaderDTO> currentReaders) {
        this.currentReaders = currentReaders;
    }

    public List<ReaderDTO> getReaders() {
        return readers;
    }

    public void setReaders(List<ReaderDTO> readers) {
        this.readers = readers;
    }

    public WriterDTO getWriter() {
        return writer;
    }

    public void setWriter(WriterDTO writer) {
        this.writer = writer;
    }
}
