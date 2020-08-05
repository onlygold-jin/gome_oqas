package com.gome.enums;

/**
 * @Description: 统一返回的 错误号，错误信息
 * @Author: WangJinYue
 * @Date: 2020/8/3 20:11
 * @Modified By:
 */
public enum ResultEnums {
    USER_NAME_ERROR(1, "用户名和本人姓名为必填项，岂能不填！"),
    PASS_ERROR(7, "新密码为必填项，岂能不填！"),
    LOGIN_ERROR(3, "用户名或密码错误!"),
    LOGIN_NULL(2, "用户名和密码为必填项,岂能不填!"),
    UPDATE_USER_ERROE(4,"此用户不存在！"),
    UPDATE_PASS_ERROE(5,"本人姓名填写有误，请重新填写！"),
    UPDATE_PASS_NULL(6,"新密码和确认密码不一致！"),
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
