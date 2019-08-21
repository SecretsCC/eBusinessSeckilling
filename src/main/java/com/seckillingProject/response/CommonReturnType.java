package com.seckillingProject.response;

public class CommonReturnType {
    //show the status of return data
    //success or fail
    private String status;

    //if status = success, return json data to front end
    //if status = fail, user general error message

    private Object data;

    //generate a common return type
    public static CommonReturnType create(Object result){
        return CommonReturnType.create(result,"success");
    }

    public static CommonReturnType create(Object result,String status){
        CommonReturnType type = new CommonReturnType();
        type.setStatus(status);
        type.setData(result);
        return type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
