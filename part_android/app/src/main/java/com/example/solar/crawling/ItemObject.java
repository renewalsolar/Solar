package com.example.solar.crawling;

public class ItemObject {
    private String title;
    private String detail_link;
    private String contents;


    public ItemObject(String title, String link, String contents){
        this.title = title;
        this.detail_link = link;
        this.contents = contents;
    }

    public String getTitle() {
        return title;
    }


    public String getDetail_link() {
        return detail_link;
    }

    public String getContents() {
        return contents;
    }
}