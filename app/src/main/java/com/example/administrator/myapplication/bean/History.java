package com.example.administrator.myapplication.bean;

import org.litepal.crud.DataSupport;

/**
 * Created by Administrator on 2017/10/8.
 */

public class History extends DataSupport {
    String serId;
    String image;
    String title;
    String content;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSerId() {
        return serId;
    }

    public void setSerId(String serId) {
        this.serId = serId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
