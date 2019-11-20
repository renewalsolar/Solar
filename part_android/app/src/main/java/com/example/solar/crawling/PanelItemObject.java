package com.example.solar.crawling;

public class PanelItemObject {
    private String title;
    private String img_url;
    private String detail_link;
    private String contents;


    public PanelItemObject(String title, String url, String link, String contents){
        this.title = title;
        this.img_url = url;
        this.detail_link = link;
        this.contents = contents;
    }


    public String getTitle() {
        return title;
    }

    public String getImg_url() {
        return img_url;
    }

    public String getDetail_link() {
        return detail_link;
    }

    public String getContents() { return contents;}

}