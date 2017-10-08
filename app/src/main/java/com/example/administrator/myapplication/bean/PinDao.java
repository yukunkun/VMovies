package com.example.administrator.myapplication.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/5/8.
 */
public class PinDao implements Serializable{

    /**
     * cate_type : 0
     * orderid : 1
     * cateid : 6
     * catename : 创意
     * alias : Idea
     * icon : http://cs.vmoiver.com/Uploads/Series/2016-04-12/570c9f3d1bc2d.jpg
     */

    private String cate_type;
    private String orderid;
    private String cateid;
    private String catename;
    private String alias;
    private String icon;

    public String getCate_type() {
        return cate_type;
    }

    public void setCate_type(String cate_type) {
        this.cate_type = cate_type;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getCateid() {
        return cateid;
    }

    public void setCateid(String cateid) {
        this.cateid = cateid;
    }

    public String getCatename() {
        return catename;
    }

    public void setCatename(String catename) {
        this.catename = catename;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Override
    public String toString() {
        return icon+""+alias+""+catename+"";
    }
}
