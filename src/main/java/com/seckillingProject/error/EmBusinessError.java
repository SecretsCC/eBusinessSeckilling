package com.seckillingProject.error;

public enum EmBusinessError implements CommonError {
    //common error 000001
    PARAMETER_VALIDATION_ERROR(10001,"wrong parameter"),
    UNKNOW_ERROR(10002,"unknow error"),

    //Error code head with 1000 means user information not right
    USER_NOT_EXIST(20001,"USER NOT FOUND"),
    USER_LOGIN_FAIL(20002,"TELEPHONE OR PASSWORD NOT CORRECT"),
    USER_NOT_LOGIN(20003,"USER NOT LOGIN"),

    STOCK_NOT_ENOUGH(30001,"STOCK NOT ENOUGH");
    ;

    private EmBusinessError(int errCode,String errMsg){
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    private int errCode;
    private String errMsg;

    @Override
    public int getErrCode() {
        return this.errCode;
    }

    @Override
    public String getErrMsg() {
        return this.errMsg;
    }

    @Override
    public CommonError setErrMsg(String errMsg) {
        this.errMsg = errMsg;
        return this;
    }
}
