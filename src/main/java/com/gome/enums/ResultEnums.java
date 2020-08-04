package com.gome.enums;

/**
 * @Description: 统一返回的 错误号，错误信息
 * @Author: WangJinYue
 * @Date: 2020/8/3 20:11
 * @Modified By:
 */
public enum ResultEnums {
    REGISTER_ERROR(1, "注册失败"),
    LOGIN_ERROR(3, "用户名或密码错误!"),
    LOGIN_NULL(2, "用户名和密码为必填项,岂能不填!"),
    ;

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
