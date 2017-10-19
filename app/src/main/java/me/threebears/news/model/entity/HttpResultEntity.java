package me.threebears.news.model.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by threebears on 2017/10/11.
 *
 * @author threebears
 */

public class HttpResultEntity<T> {

    private int code;
    private String msg;
    @SerializedName("newslist")
    private List<T> listNews;

    public boolean isSuccess() {
        return code == 200;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<T> getListNews() {
        return listNews;
    }

    public void setListNews(List<T> listNews) {
        this.listNews = listNews;
    }

    @Override
    public String toString() {
        return "HttpResultEntity{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", listNews=" + listNews +
                '}';
    }
}
