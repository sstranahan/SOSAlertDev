package com.example.sosalertdev;

public class NewsItem {
    String abs;
    String url;
    String snippet;
    String leadPar;

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