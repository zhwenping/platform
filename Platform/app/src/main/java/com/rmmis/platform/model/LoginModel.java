package com.rmmis.platform.model;

import java.util.List;

/**
 * Created by Edianzu on 2018/10/24.
 * 登录
 */

public class LoginModel {

    /**
     * result : {"userInfo":{"user_id":"1234567891","real_name":"陈杉","picture":null,"sex":null,"phone":"1234567891","email":null,"status":"1"},"org":[{"user_id":"1234567891","s_deptcode":"1234567","s_deptname":"乘务车间","application_id":"1001","application_name":"认定单应用","bussiness_code":"3","bussiness_name":"源头质量业务","role_code":"9","role_name":"随车机械师"},{"user_id":"1234567891","s_deptcode":"021","s_deptname":"成都东动车组运用所","application_id":"1000","application_name":"申请单应用","bussiness_code":"3","bussiness_name":"源头质量业务","role_code":"9","role_name":"随车 机械师"}],"plcatformApplication":{"application_icon":"30287916300193","application_name":"平 台应用","description":"管理平台应用的版本","application_state":"1","application_system":"0","application_package":null,"version_type":"1","release_time":"2018/10/20 10:00:33","status":null,"application_file":null,"remark":"12345","version_number":"1.1","plist_path":null,"fore_version_number":"1.1"}}
     * stateCode : 状态码   1：表示成功 0：表示失败
     * message : 登陆成功
     */

    private ResultBean result;
    private String stateCode;
    private String message;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

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

    public static class ResultBean {
        /**
         * userInfo :  用户基本信息 {"user_id":"1234567891","real_name":"陈杉","picture":null,"sex":null,"phone":"1234567891","email":null,"status":"1"}
         * org :  子应用信息 [{"user_id":"1234567891","s_deptcode":"1234567","s_deptname":"乘务车间","application_id":"1001","application_name":"认定单应用","bussiness_code":"3","bussiness_name":"源头质量业务","role_code":"9","role_name":"随车机械师"},{"user_id":"1234567891","s_deptcode":"021","s_deptname":"成都东动车组运用所","application_id":"1000","application_name":"申请单应用","bussiness_code":"3","bussiness_name":"源头质量业务","role_code":"9","role_name":"随车 机械师"}]
         * plcatformApplication : 平台版本信息  {"application_icon":"30287916300193","application_name":"平 台应用","description":"管理平台应用的版本","application_state":"1","application_system":"0","application_package":null,"version_type":"1","release_time":"2018/10/20 10:00:33","status":null,"application_file":null,"remark":"12345","version_number":"1.1","plist_path":null,"fore_version_number":"1.1"}
         */

        private UserInfoBean userInfo;

        private List<OrgBean> org;

        public UserInfoBean getUserInfo() {
            return userInfo;
        }

        public void setUserInfo(UserInfoBean userInfo) {
            this.userInfo = userInfo;
        }



        public List<OrgBean> getOrg() {
            return org;
        }

        public void setOrg(List<OrgBean> org) {
            this.org = org;
        }

        public static class UserInfoBean {
            /**
             * user_id : 账号
             * real_name :真实姓名
             * picture : 头像（接口文档没写）
             * sex : 性别
             * phone : 电话
             * email : 邮箱
             * status : 认证状态(0不认证1：认证)
             */

            private String user_id;
            private String real_name;
            private String picture;
            private String sex;
            private String phone;
            private String email;
            private String status;

            public String getUser_id() {
                return user_id;
            }

            public void setUser_id(String user_id) {
                this.user_id = user_id;
            }

            public String getReal_name() {
                return real_name;
            }

            public void setReal_name(String real_name) {
                this.real_name = real_name;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getPicture() {
                return picture;
            }

            public void setPicture(String picture) {
                this.picture = picture;
            }

            public String getSex() {
                return sex;
            }

            public void setSex(String sex) {
                this.sex = sex;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }
        }


        public static class OrgBean {
            /**
             * user_id : 账号
             * s_deptcode : 部门编码
             * s_deptname : 部门名称
             * application_id : 应用ID
             * application_name : 应用名称
             * bussiness_code : 业务编码
             * bussiness_name : 业务名称
             * role_code : 角色编码
             * role_name : 角色名称
             */

            private String user_id;
            private String s_deptcode;
            private String s_deptname;
            private String application_id;
            private String application_name;
            private String bussiness_code;
            private String bussiness_name;
            private String role_code;
            private String role_name;

            public String getUser_id() {
                return user_id;
            }

            public void setUser_id(String user_id) {
                this.user_id = user_id;
            }

            public String getS_deptcode() {
                return s_deptcode;
            }

            public void setS_deptcode(String s_deptcode) {
                this.s_deptcode = s_deptcode;
            }

            public String getS_deptname() {
                return s_deptname;
            }

            public void setS_deptname(String s_deptname) {
                this.s_deptname = s_deptname;
            }

            public String getApplication_id() {
                return application_id;
            }

            public void setApplication_id(String application_id) {
                this.application_id = application_id;
            }

            public String getApplication_name() {
                return application_name;
            }

            public void setApplication_name(String application_name) {
                this.application_name = application_name;
            }

            public String getBussiness_code() {
                return bussiness_code;
            }

            public void setBussiness_code(String bussiness_code) {
                this.bussiness_code = bussiness_code;
            }

            public String getBussiness_name() {
                return bussiness_name;
            }

            public void setBussiness_name(String bussiness_name) {
                this.bussiness_name = bussiness_name;
            }

            public String getRole_code() {
                return role_code;
            }

            public void setRole_code(String role_code) {
                this.role_code = role_code;
            }

            public String getRole_name() {
                return role_name;
            }

            public void setRole_name(String role_name) {
                this.role_name = role_name;
            }
        }
    }
}
