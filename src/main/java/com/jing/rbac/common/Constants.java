package com.jing.rbac.common;

/**
 * @Author: huangjingyan-200681
 * @Date: 2021/3/29 15:12
 * @Mail: huangjingyan@eastmoney.com
 * @Description: TODO
 * @Version: 1.0
 **/
public class Constants {


    public static enum status{

        NORMAL(1,"正常"),
        DISCARD(2,"作废");

        status(Integer code, String des) {
            this.code = code;
            this.des = des;
        }

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getDes() {
            return des;
        }

        public void setDes(String des) {
            this.des = des;
        }

        private Integer code;

        private String des;
    }

}
