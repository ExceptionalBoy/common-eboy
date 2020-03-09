package com.eboy.common.enums;

public enum SystemCodeEnum {

    SUCCESS("000000000","操作成功"),
    ERROR("11111111","操作失败"),
    SYSTEM_ERROR("22222222","系统异常"),
    PARAM_ERROR("33333333","参数错误"),
    DB_ERROR("44444444","数据库异常"),
    MAIL_ERROR("55555555","邮件发送失败");

    SystemCodeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private String code;

    private String msg;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
