package com.mcc.XUtilsTest.model;

/**
 * user:mcc
 * Data:15-4-11
 */

/**
 * BitmapUtils中使用的实体类
 */
public class Book {
    private String title;
    private String author;
    /**
     * 图片的网址
     */
    private String cover;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }
}
