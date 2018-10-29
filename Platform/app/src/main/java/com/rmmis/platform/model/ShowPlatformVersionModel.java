package com.rmmis.platform.model;

/**
 * Created by Edianzu on 2018/10/26.
 * 版本更新
 */

public class ShowPlatformVersionModel {
    private String stateCode;
    private String message;
    private PlcatformApplicationBean result;

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

    public PlcatformApplicationBean getResult() {
        return result;
    }

    public void setResult(PlcatformApplicationBean result) {
        this.result = result;
    }
    public static class PlcatformApplicationBean {
        /**
         * application_icon : 应用图标
         * application_name : 应用名称
         * description : 描述
         * application_state : 应用状态（0不上架1上架）
         * application_system : 应用系统（0 android  1 ios）
         * application_package : 应用包名
         * version_type : 版本类型（0不强制升级 1强制升级）
         * release_time : 发布时间
         * status : 发布状态（0不发布 1发布）
         * application_file : 当前应用包（android）:下载地址
         * remark : 版本描述
         * version_number : 当前版本号
         * plist_path : Plist文件路径（ios）
         * fore_version_number : 强制版本号
         */

        private String application_icon;
        private String application_name;
        private String description;
        private String application_state;
        private String application_system;
        private String application_package;
        private String version_type;
        private String release_time;
        private String status;
        private String application_file;
        private String remark;
        private String version_number;
        private String plist_path;
        private String fore_version_number;

        public String getApplication_icon() {
            return application_icon;
        }

        public void setApplication_icon(String application_icon) {
            this.application_icon = application_icon;
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

        public String getApplication_state() {
            return application_state;
        }

        public void setApplication_state(String application_state) {
            this.application_state = application_state;
        }

        public String getApplication_system() {
            return application_system;
        }

        public void setApplication_system(String application_system) {
            this.application_system = application_system;
        }





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

        public String getApplication_package() {
            return application_package;
        }

        public void setApplication_package(String application_package) {
            this.application_package = application_package;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getApplication_file() {
            return application_file;
        }

        public void setApplication_file(String application_file) {
            this.application_file = application_file;
        }

        public String getPlist_path() {
            return plist_path;
        }

        public void setPlist_path(String plist_path) {
            this.plist_path = plist_path;
        }

        public String getFore_version_number() {
            return fore_version_number;
        }

        public void setFore_version_number(String fore_version_number) {
            this.fore_version_number = fore_version_number;
        }
    }

}
