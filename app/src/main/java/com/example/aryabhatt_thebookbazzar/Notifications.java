package com.example.aryabhatt_thebookbazzar;

public class Notifications {

    private String titles, contents;

    public Notifications() {
    }

    public Notifications(String titles, String contents) {
        this.titles = titles;
        this.contents = contents;
    }

    public String getTitles() {
        return titles;
    }

    public void setTitles(String titles) {
        this.titles = titles;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }
}
