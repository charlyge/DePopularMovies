package com.charlyge.android.depopularmovies.model;

public class Review {

    private String author;
    private String content;
    private String id;
    private String url;

    public Review(String author,String content, String id,String url){
        this.author=author;
        this.content =content;
        this.id =id;
        this.url =url;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }
}
