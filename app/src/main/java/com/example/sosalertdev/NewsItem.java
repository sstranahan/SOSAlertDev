package com.example.sosalertdev;

public class NewsItem {
    public String abs;
    public String url;
    public String snippet;
    public String leadPar;
    public String imgURL;

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public NewsItem() {
    }

    public NewsItem(String abs, String url, String snippet, String leadPar, String imgURL) {
        this.abs = abs;
        this.url = url;
        this.snippet = snippet;
        this.leadPar = leadPar;
        this.imgURL = imgURL;
    }

    public String getLeadPar() {
        return leadPar;
    }

    public void setLeadPar(String leadPar) {
        this.leadPar = leadPar;
    }

    public String getAbs() {
        return abs;
    }

    public void setAbs(String abs) {
        this.abs = abs;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSnippet() {
        return snippet;
    }

    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }
}