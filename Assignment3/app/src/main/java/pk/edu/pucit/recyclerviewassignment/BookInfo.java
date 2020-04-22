package pk.edu.pucit.recyclerviewassignment;

import android.icu.lang.UProperty;

public class BookInfo extends Object {
    private String bookTitle;
    private String bookInfo;
    private String bookUrl;
    private String bookLevel;
    private String bookCover;

    public BookInfo() {
    }

    // constructor
    public BookInfo(String bookTitle, String bookInfo, String bookUrl, String bookLevel, String bookCover) {
        this.bookTitle = bookTitle;
        this.bookInfo = bookInfo;
        this.bookUrl = bookUrl;
        this.bookLevel = bookLevel;
        this.bookCover = bookCover;
    }

    // setters
    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public void setBookInfo(String bookInfo) {
        this.bookInfo = bookInfo;
    }

    public void setBookUrl(String bookUrl) {
        this.bookUrl = bookUrl;
    }

    public void setBookLevel(String bookLevel) {
        this.bookLevel = bookLevel;
    }

    public void setBookCover(String bookCover) {
        this.bookCover = bookCover;
    }

    //getters
    public String getBookTitle() {
        return bookTitle;
    }

    public String getBookInfo() {
        return bookInfo;
    }

    public String getBookUrl() {
        return bookUrl;
    }

    public String getBookLevel() {
        return bookLevel;
    }

    public String getBookCover() {
        return bookCover;
    }
}
