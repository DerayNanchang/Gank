package com.audio.administrator.ganhuo.modules.android.bean;

import java.util.List;

/**
 * Author: Chris
 * Blog: https://www.jianshu.com/u/a3534a2292e8
 * Date: 2019/1/23
 * Description
 */
public class Bean {

    /**
     * _id : 5c2df1479d2122759a04b597
     * createdAt : 2019-01-03T11:25:59.115Z
     * desc : Android 一键加入侧滑返回 (类似“小米MIX”和新版“即刻”滑动返回)
     * images : ["https://ww1.sinaimg.cn/large/0073sXn7ly1fze96rdfhmg308w0ft7wh","https://ww1.sinaimg.cn/large/0073sXn7ly1fze96s6tdag308w0ftjvw"]
     * publishedAt : 2019-01-21T00:00:00.0Z
     * source : web
     * type : Android
     * url : https://github.com/qinci/AndroidSlideBack
     * used : true
     * who : qinci
     */

    private String _id;
    private String createdAt;
    private String desc;
    private String publishedAt;
    private String source;
    private String type;
    private String url;
    private boolean used;
    private String who;
    private List<String> images;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}
