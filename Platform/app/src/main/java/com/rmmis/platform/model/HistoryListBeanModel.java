package com.rmmis.platform.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Edianzu on 2018/10/22.
 * 历史推送信息
 */

public class HistoryListBeanModel implements Serializable{
    /**
     * historyList :  推送历史集合
     * stateCode : 状态码  1：表示成功   0：表示失败:23","
     * message : 返回值消息 成功或系统异常
     */

    private int stateCode;
    private String message;
    private ArrayList<HistoryListBean> historyList;

    public int getStateCode() {
        return stateCode;
    }

    public void setStateCode(int stateCode) {
        this.stateCode = stateCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList <HistoryListBean> getHistoryList() {
        return historyList;
    }

    public void setHistoryList(ArrayList<HistoryListBean> historyList) {
        this.historyList = historyList;
    }

    public static class HistoryListBean implements Serializable{
        /**
         * push_id : 推送ID
         * push_type : 推送消息类型 1通知 2消息 3公告
         * push_title : 推送标题
         * push_content : 推送内容
         * push_link : 富文本地址
         * push_userId : 推送者id/推送系统code
         * push_userName : 推送人名称/推送系统名称
         * send_time : 推送时间
         * createtime : 创建时间
         * push_media : 类型
         * errorNum : 错误数据
         * to_user : 推送给谁
         */

        private String id;
        private String push_type;
        private String push_title;
        private String push_content;
        private String push_link;
        private String push_userId;
        private String push_userName;
        private String send_time;
        private String createtime;
        private String push_media;
        private String errorNum;
        private String to_user;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPush_type() {
            return push_type;
        }

        public void setPush_type(String push_type) {
            this.push_type = push_type;
        }

        public String getPush_title() {
            return push_title;
        }

        public void setPush_title(String push_title) {
            this.push_title = push_title;
        }

        public String getPush_content() {
            return push_content;
        }

        public void setPush_content(String push_content) {
            this.push_content = push_content;
        }

        public String getPush_link() {
            return push_link;
        }

        public void setPush_link(String push_link) {
            this.push_link = push_link;
        }

        public String getPush_userId() {
            return push_userId;
        }

        public void setPush_userId(String push_userId) {
            this.push_userId = push_userId;
        }

        public String getPush_userName() {
            return push_userName;
        }

        public void setPush_userName(String push_userName) {
            this.push_userName = push_userName;
        }

        public String getSend_time() {
            return send_time;
        }

        public void setSend_time(String send_time) {
            this.send_time = send_time;
        }

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public String getPush_media() {
            return push_media;
        }

        public void setPush_media(String push_media) {
            this.push_media = push_media;
        }

        public String getErrorNum() {
            return errorNum;
        }

        public void setErrorNum(String errorNum) {
            this.errorNum = errorNum;
        }

        public String getTo_user() {
            return to_user;
        }

        public void setTo_user(String to_user) {
            this.to_user = to_user;
        }
    }

}
