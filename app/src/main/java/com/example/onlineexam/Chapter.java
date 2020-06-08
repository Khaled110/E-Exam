package com.example.onlineexam;

public class Chapter {
    private String name,url;

    public Chapter(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public Chapter() {
    }

    public Chapter(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
