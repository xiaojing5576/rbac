package com.jing.rbac.response;

public enum ResultCode {
    //全局
    OK(0, "ok"),
    PARAMETER_NOT_EMPTY(401, "参数不合法"),
    ERROR(500, "系统维护"),
    UNAUTHORIZED(403, "未授权"),
    SESSION_ERR(405, "未登录或身份认证已过期"),

    //登录相关
//    TOKEN_VALID_ERROR(0x10101,"未登录或身份认证已过期"),

    //用户
    USER_NOTEXIST(10201, "用户不存在"),
    USER_EXISTED(10202, "用户已存在"),
    USER_STATUS_UNVALID(10203, "当前用户状态不合法"),
    //权限相关
    HAVE_NO_RIGHT(10301, "权限不足"),
    //租赁期
    TENANCY_PERIOD_DETAIL_HAS_INVALID_DATA(10401,"租赁期数据存在非法数据"),
    TENANCY_PERIOD_DETAIL_STARTED_CANNOT_MODIFY(10402,"已经开始计量的租赁期无法调整结束日期到本月之前"),
    //合同相关
    CONTRACT_EXIST(10501,"合同已存在"),
    CONTRACT_UNCONFIRM(10502,"合同信息尚未确认，无法完成该操作"),
    CONTRACT_CONFIRMED(10503,"合同信息已确认或处于不可确认状态，无法完成该操作"),
    CONTRACT_STATUS_INVALID(10504,"合同租赁信息非法状态，无法执行当前操作"),
    //核算相关
    CALCULATION_PERIOD_ERROR(10601,"待生成凭证期间不合法")
    ;

    private Integer code;

    private String msg;

    ResultCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static ResultCode getResultCode(int code) {
        ResultCode resultCode = null;
        for (ResultCode result : values()) {
            if (code == result.getCode().intValue()) {
                resultCode = result;
                break;
            }
        }
        return resultCode;
    }

    public Integer getCode() {
        return Integer.valueOf(this.code);
    }

    public void setCode(Integer code) {
        this.code = code.intValue();
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
