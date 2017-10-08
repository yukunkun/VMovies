package com.example.administrator.myapplication.bean;

import com.google.gson.Gson;

import java.util.List;

/**
 * Created by Administrator on 2016/5/8.
 */
public class Info {

    /**
     * status : 0
     * msg : OK
     * data : [{"postid":"48990","title":"超精致谐趣3D动画《逃跑的衣服》","pid":"1","app_fu_title":"","is_xpc":"0","is_promote":"0","is_xpc_zp":"0","is_xpc_cp":"0","is_xpc_fx":"0","is_album":"0","tags":"","recent_hot":"0","discussion":"0","image":"http://cs.vmoiver.com/Uploads/cover/2016-05-06/572ca177633ef_cut.jpeg","rating":"7.9","duration":"454","publish_time":"1462665840","like_num":"132","share_num":"256","cates":[{"cateid":"16","catename":"动画"}],"request_url":"http://app.vmoiver.com/48990?qingapp=app_new"},{"postid":"48987","title":"超帅气COS街头跑酷《镜之边缘》","pid":"1","app_fu_title":"","is_xpc":"0","is_promote":"0","is_xpc_zp":"0","is_xpc_cp":"0","is_xpc_fx":"0","is_album":"0","tags":"","recent_hot":"0","discussion":"0","image":"http://cs.vmoiver.com/Uploads/cover/2016-05-06/572c9c476810a_cut.jpeg","rating":"7.0","duration":"187","publish_time":"1462665780","like_num":"124","share_num":"251","cates":[{"cateid":"11","catename":"旅行"}],"request_url":"http://app.vmoiver.com/48987?qingapp=app_new"},{"postid":"48977","title":"无以回报的爱","pid":"1","app_fu_title":"母亲节暖心短片专题","is_xpc":"0","is_promote":"0","is_xpc_zp":"0","is_xpc_cp":"0","is_xpc_fx":"0","is_album":"1","tags":"","recent_hot":"0","discussion":"0","image":"http://cs.vmoiver.com/Uploads/cover/2016-05-05/572ae45159ce5_cut.jpeg","rating":"8.0","duration":"71","publish_time":"1462665720","like_num":"191","share_num":"860","cates":[{"cateid":"7","catename":"励志"}],"request_url":"http://app.vmoiver.com/48977?qingapp=app_new"},{"postid":"48994","title":"摩洛哥异域风情延时摄影《第三只眼》","pid":"1","app_fu_title":"","is_xpc":"0","is_promote":"0","is_xpc_zp":"0","is_xpc_cp":"0","is_xpc_fx":"0","is_album":"0","tags":"","recent_hot":"0","discussion":"0","image":"http://cs.vmoiver.com/Uploads/cover/2016-05-06/572ca098719c2_cut.jpeg","rating":"7.8","duration":"338","publish_time":"1462665660","like_num":"106","share_num":"211","cates":[{"cateid":"11","catename":"旅行"}],"request_url":"http://app.vmoiver.com/48994?qingapp=app_new"},{"postid":"48983","title":"母亲节泪目微电影《来自星星的妈妈》","pid":"1","app_fu_title":"","is_xpc":"1","is_promote":"0","is_xpc_zp":"1","is_xpc_cp":"0","is_xpc_fx":"0","is_album":"0","tags":"","recent_hot":"0","discussion":"0","image":"http://cs.vmoiver.com/Uploads/cover/2016-05-05/572b27b735203_cut.jpeg","rating":"7.1","duration":"341","publish_time":"1462665600","like_num":"148","share_num":"397","cates":[{"cateid":"13","catename":"广告"}],"request_url":"http://app.vmoiver.com/48983?qingapp=app_new"},{"postid":"49000","title":"母亲节特辑 丨 她只希望你不要受伤","pid":"1","app_fu_title":"","is_xpc":"0","is_promote":"0","is_xpc_zp":"0","is_xpc_cp":"0","is_xpc_fx":"0","is_album":"0","tags":"","recent_hot":"0","discussion":"0","image":"http://cs.vmoiver.com/Uploads/cover/2016-05-07/572df933afa3b_cut.jpeg","rating":"7.5","duration":"262","publish_time":"1462630903","like_num":"235","share_num":"575","cates":[{"cateid":"78","catename":"生活"}],"request_url":"http://app.vmoiver.com/49000?qingapp=app_new"},{"postid":"49001","title":"母亲节特辑 丨 你有多久没吃妈妈菜","pid":"1","app_fu_title":"","is_xpc":"1","is_promote":"0","is_xpc_zp":"1","is_xpc_cp":"0","is_xpc_fx":"0","is_album":"0","tags":"","recent_hot":"0","discussion":"0","image":"http://cs.vmoiver.com/Uploads/cover/2016-05-07/572def84ddbb6_cut.jpeg","rating":"8.0","duration":"267","publish_time":"1462628569","like_num":"283","share_num":"619","cates":[{"cateid":"78","catename":"生活"}],"request_url":"http://app.vmoiver.com/49001?qingapp=app_new"},{"postid":"48886","title":"泰国母亲节温情广告《不会忘记的事》","pid":"1","app_fu_title":"","is_xpc":"0","is_promote":"0","is_xpc_zp":"0","is_xpc_cp":"0","is_xpc_fx":"0","is_album":"0","tags":"","recent_hot":"0","discussion":"0","image":"http://cs.vmoiver.com/Uploads/cover/2016-04-21/5718c5e46f942_cut.jpeg","rating":"8.0","duration":"229","publish_time":"1462579440","like_num":"420","share_num":"1071","cates":[{"cateid":"13","catename":"广告"}],"request_url":"http://app.vmoiver.com/48886?qingapp=app_new"},{"postid":"48995","title":"尼泊尔震撼行摄短片《喜马拉雅》","pid":"1","app_fu_title":"","is_xpc":"0","is_promote":"0","is_xpc_zp":"0","is_xpc_cp":"0","is_xpc_fx":"0","is_album":"0","tags":"","recent_hot":"0","discussion":"0","image":"http://cs.vmoiver.com/Uploads/cover/2016-05-06/572c97f86d15a_cut.jpeg","rating":"7.5","duration":"292","publish_time":"1462579380","like_num":"421","share_num":"800","cates":[{"cateid":"11","catename":"旅行"}],"request_url":"http://app.vmoiver.com/48995?qingapp=app_new"},{"postid":"48989","title":"超视觉震撼魔幻现实短片《情欲的潮起潮落》","pid":"1","app_fu_title":"","is_xpc":"0","is_promote":"0","is_xpc_zp":"0","is_xpc_cp":"0","is_xpc_fx":"0","is_album":"0","tags":"","recent_hot":"0","discussion":"0","image":"http://cs.vmoiver.com/Uploads/cover/2016-05-06/572c96de37804_cut.jpeg","rating":"8.5","duration":"698","publish_time":"1462579320","like_num":"506","share_num":"1033","cates":[{"cateid":"23","catename":"科幻"}],"request_url":"http://app.vmoiver.com/48989?qingapp=app_new"}]
     */

    private String status;
    private String msg;
    /**
     * postid : 48990
     * title : 超精致谐趣3D动画《逃跑的衣服》
     * pid : 1
     * app_fu_title :
     * is_xpc : 0
     * is_promote : 0
     * is_xpc_zp : 0
     * is_xpc_cp : 0
     * is_xpc_fx : 0
     * is_album : 0
     * tags :
     * recent_hot : 0
     * discussion : 0
     * image : http://cs.vmoiver.com/Uploads/cover/2016-05-06/572ca177633ef_cut.jpeg
     * rating : 7.9
     * duration : 454
     * publish_time : 1462665840
     * like_num : 132
     * share_num : 256
     * cates : [{"cateid":"16","catename":"动画"}]
     * request_url : http://app.vmoiver.com/48990?qingapp=app_new
     */

    private List<DataBean> data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }


    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private String postid;
        private String title;
        private String pid;
        private String app_fu_title;
        private String is_xpc;
        private String is_promote;
        private String is_xpc_zp;
        private String is_xpc_cp;
        private String is_xpc_fx;
        private String is_album;
        private String tags;
        private String recent_hot;
        private String discussion;
        private String image;
        private String rating;
        private String duration;
        private String publish_time;
        private String like_num;
        private String share_num;
        private String request_url;
        /**
         * cateid : 16
         * catename : 动画
         */

        private List<CatesBean> cates;

        public String getPostid() {
            return postid;
        }

        public void setPostid(String postid) {
            this.postid = postid;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getPid() {
            return pid;
        }

        public void setPid(String pid) {
            this.pid = pid;
        }

        public String getApp_fu_title() {
            return app_fu_title;
        }

        public void setApp_fu_title(String app_fu_title) {
            this.app_fu_title = app_fu_title;
        }

        public String getIs_xpc() {
            return is_xpc;
        }

        public void setIs_xpc(String is_xpc) {
            this.is_xpc = is_xpc;
        }

        public String getIs_promote() {
            return is_promote;
        }

        public void setIs_promote(String is_promote) {
            this.is_promote = is_promote;
        }

        public String getIs_xpc_zp() {
            return is_xpc_zp;
        }

        public void setIs_xpc_zp(String is_xpc_zp) {
            this.is_xpc_zp = is_xpc_zp;
        }

        public String getIs_xpc_cp() {
            return is_xpc_cp;
        }

        public void setIs_xpc_cp(String is_xpc_cp) {
            this.is_xpc_cp = is_xpc_cp;
        }

        public String getIs_xpc_fx() {
            return is_xpc_fx;
        }

        public void setIs_xpc_fx(String is_xpc_fx) {
            this.is_xpc_fx = is_xpc_fx;
        }

        public String getIs_album() {
            return is_album;
        }

        public void setIs_album(String is_album) {
            this.is_album = is_album;
        }

        public String getTags() {
            return tags;
        }

        public void setTags(String tags) {
            this.tags = tags;
        }

        public String getRecent_hot() {
            return recent_hot;
        }

        public void setRecent_hot(String recent_hot) {
            this.recent_hot = recent_hot;
        }

        public String getDiscussion() {
            return discussion;
        }

        public void setDiscussion(String discussion) {
            this.discussion = discussion;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getRating() {
            return rating;
        }

        public void setRating(String rating) {
            this.rating = rating;
        }

        public String getDuration() {
            return duration;
        }

        public void setDuration(String duration) {
            this.duration = duration;
        }

        public String getPublish_time() {
            return publish_time;
        }

        public void setPublish_time(String publish_time) {
            this.publish_time = publish_time;
        }

        public String getLike_num() {
            return like_num;
        }

        public void setLike_num(String like_num) {
            this.like_num = like_num;
        }

        public String getShare_num() {
            return share_num;
        }

        public void setShare_num(String share_num) {
            this.share_num = share_num;
        }

        public String getRequest_url() {
            return request_url;
        }

        public void setRequest_url(String request_url) {
            this.request_url = request_url;
        }

        public List<CatesBean> getCates() {
            return cates;
        }

        public void setCates(List<CatesBean> cates) {
            this.cates = cates;
        }

        public static class CatesBean {
            private String cateid;
            private String catename;

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
        }
    }
}
