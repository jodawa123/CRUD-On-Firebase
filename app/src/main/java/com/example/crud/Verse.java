package com.example.crud;

public class Verse {
    String key, verse,book;


    public Verse(String key, String verse, String book) {
        this.key = key;
        this.verse = verse;
        this.book = book;
    }

    public Verse() {

    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getVerse() {
        return verse;
    }

    public void setVerse(String verse) {
        this.verse = verse;
    }

    public String getBook() {
        return book;
    }

    public void setBook(String book) {
        this.book = book;
    }
}
