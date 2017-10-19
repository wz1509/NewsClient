package me.threebears.news.model.entity;

import java.io.Serializable;

/**
 * Created by threebears on 2017/10/11.
 * @author threebears
 * 新闻实体类
 */
public class NewsEntity implements Serializable{

    /**
     * ctime : 2017-10-11 10:34
     * title : 这4种胃病会变成胃癌？
     * description : 网易健康
     * picUrl : http://imgsize.ph.126.net/?imgurl=http://cms-bucket.nosdn.127.net/a906733554aa4e9280e6687bc631bee620171011103434.png_150x100x1x85.jpg
     * url : http://jiankang.163.com/17/1011/10/D0F8TPPA0038804U.html
     */

    private String ctime;
    private String title;
    private String description;
    private String picUrl;
    private String url;

    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
