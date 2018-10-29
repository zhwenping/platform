package com.rmmis.platform.model;

import java.util.List;

/**
 * Created by Edianzu on 2018/10/25.
 */

public class ShowApplicationModel {


    /**
     * stateCode : 状态码  1：表示成功 0：表示失败
     * message : 返回值消息  成功，系统异常
     * result : 待办列表信息，list类型 [{"application_id":"1001","application_name":"内置应用","description":"应用是*******（描述信息）","creattime":"2018-10-17","application_icon":"23739797642171","application_system":"0","application_type":"1","application_invocation_class":"***controller","application_state":"0","version":{"version_type":"0","release_time":"2018-10-18","status":"1","remark":"该版本更新了。。","version_number":"1.1","force_version_number":"1.0","application_package":"com.enway.soft","package_file":"******","plist_path":""}},{"application_name":"第三方应用","description":"应用是*******（描述信息）","creattime":"2018-10-17","application_icon":"23739797642171","application_system":"1","application_type":"3","application_invocation_class":"","application_state":"0","version":{"version_type":"0","release_time":"2018-10-18","status":"1","remark":"该版本更新了。。","version_number":"1.1","force_version_number":"1.0","application_package":"com.enway.soft","package_file":"","plist_path":"https://app.rails.cn/MobilePlatform//TYsuggest.plist"}}]
     */

    private String status;
    private String message;


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private List<ResultBean> result;



    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * application_id : 应用AppId
         * application_name : 应用名称
         * application_package:应用包名
         * description : 应用描述
         * creattime : 应用创建时间
         * application_icon : 应用图标
         * application_system : 应用系统（0:android  1:ios）
         * application_type : 应用类型（0：h5 1：内置   2：第三方
         * application_invocation_class :应用调用初始类（h5 ：url  内置：ios  controller  android : activity）
         * application_state : 应用状态（0不上架1上架）
         * role_code : 角色编号
         * role_name : 角色编码
         * version : {"version_type":"0","release_time":"2018-10-18","status":"1","remark":"该版本更新了。。","version_number":"1.1","force_version_number":"1.0","application_package":"com.enway.soft","package_file":"******","plist_path":""}
         */

        private String application_id;
        private String application_name;
        private String description;
        private String creattime;
        private String application_icon;
        private String application_system;
        private String application_type;
        private String application_invocation_class;
        private String application_state;
        private VersionBean version;
        private String role_code;
        private String application_package;

        public String getApplication_package() {
            return application_package;
        }

        public void setApplication_package(String application_package) {
            this.application_package = application_package;
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

        private String role_name;

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

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getCreattime() {
            return creattime;
        }

        public void setCreattime(String creattime) {
            this.creattime = creattime;
        }

        public String getApplication_icon() {
            return application_icon;
        }

        public void setApplication_icon(String application_icon) {
            this.application_icon = application_icon;
        }

        public String getApplication_system() {
            return application_system;
        }

        public void setApplication_system(String application_system) {
            this.application_system = application_system;
        }

        public String getApplication_type() {
            return application_type;
        }

        public void setApplication_type(String application_type) {
            this.application_type = application_type;
        }

        public String getApplication_invocation_class() {
            return application_invocation_class;
        }

        public void setApplication_invocation_class(String application_invocation_class) {
            this.application_invocation_class = application_invocation_class;
        }

        public String getApplication_state() {
            return application_state;
        }

        public void setApplication_state(String application_state) {
            this.application_state = application_state;
        }

        public VersionBean getVersion() {
            return version;
        }

        public void setVersion(VersionBean version) {
            this.version = version;
        }

        public static class VersionBean {
            /**
             * version_type : 是否强制升级（0不强制1强制）
             * release_time : 版本上传时间
             * status :状态（0不发布1 发布）
             * remark :版本描述
             * version_number : 版本号
             * force_version_number : 强制版本号（最低使用版本）
             * application_package : 应用包名
             * package_file : Android 需要填写文件路径
             * plist_path :Ios 需要填写文件路径
             */

            private String version_type;
            private String release_time;
            private String status;
            private String remark;
            private String version_number;
            private String force_version_number;
            private String application_package;
            private String package_file;
            private String plist_path;

            public String getVersion_type() {
                return version_type;
            }

            public void setVersion_type(String version_type) {
                this.version_type = version_type;
            }

            public String getRelease_time() {
                return release_time;
            }

            public void setRelease_time(String release_time) {
                this.release_time = release_time;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public String getVersion_number() {
                return version_number;
            }

            public void setVersion_number(String version_number) {
                this.version_number = version_number;
            }

            public String getForce_version_number() {
                return force_version_number;
            }

            public void setForce_version_number(String force_version_number) {
                this.force_version_number = force_version_number;
            }

            public String getApplication_package() {
                return application_package;
            }

            public void setApplication_package(String application_package) {
                this.application_package = application_package;
            }

            public String getPackage_file() {
                return package_file;
            }

            public void setPackage_file(String package_file) {
                this.package_file = package_file;
            }

            public String getPlist_path() {
                return plist_path;
            }

            public void setPlist_path(String plist_path) {
                this.plist_path = plist_path;
            }
        }
    }
}
