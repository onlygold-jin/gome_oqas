package com.gome.enums;

/**
 * @Description:
 * @Author: WangJinYue
 * @Date: 2020/8/3 20:11
 * @Modified By:
 */
public enum ResultEnums {
    REGISTER_ERROR(1, "注册失败"),
    LOGIN_ERROR(2, "登录失败");

    private Integer status;
    private String msg;

    ResultEnums(Integer status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public Integer getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }
}
