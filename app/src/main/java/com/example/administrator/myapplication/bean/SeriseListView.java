package com.example.administrator.myapplication.bean;

/**
 * Created by Administrator on 2016/5/15.
 */
public class SeriseListView {

    /**
     * series_postid : 1755
     * number : 7
     * title : 夏日防蚊必备
     * addtime : 2016.05.11
     * duration : 193
     * thumbnail : http://cs.vmoiver.com/Uploads/Series/2016-05-11/57330a2c4a045.jpeg
     * source_link : http://v.youku.com/v_show/id_XMTU2NjMyNDc4OA==.html?
     */

    private String series_postid;
    private String number;
    private String title;
    private String addtime;
    private String duration;
    private String thumbnail;
    private String source_link;

    public String getSeries_postid() {
        return series_postid;
    }

    public void setSeries_postid(String series_postid) {
        this.series_postid = series_postid;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getSource_link() {
        return source_link;
    }

    public void setSource_link(String source_link) {
        this.source_link = source_link;
    }
}
