package com.personal_book_library_api.demo.dtos;

public class PageDTO {
    private int page;
    private int size;
    private String content;

    public PageDTO(int page, int size, String content) {
        this.page = page;
        this.size = size;
        this.content = content;
    }

    public PageDTO() {
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
