package com.example.onlineexam;

public class UploadPDFInfo {
    private String name,url;

    public UploadPDFInfo() {
    }

    public UploadPDFInfo(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
