package com.rmmis.platform.model;

/**
 * Created by Edianzu on 2018/10/26.
 * 修改密码
 */

public class UpdatePasswordModel {

    /**
     * stateCode : 1
     * message : 密码修改成功
     */

    private String stateCode;
    private String message;

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
