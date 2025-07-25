package com.soocil.ebook;

public class BookModel {
    private final int id;
    private final String title;
    private final String author;
    private final int pages;

    public BookModel(int id, String title, String author, int pages) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.pages = pages;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getPages() {
        return pages;
    }
}