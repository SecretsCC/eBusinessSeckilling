package com.seckillingProject.controller;

import com.seckillingProject.error.BusinessException;
import com.seckillingProject.error.EmBusinessError;
import com.seckillingProject.response.CommonReturnType;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class BaseController {
    //exception handler solve
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Object handlerException(HttpServletRequest request, Exception ex){
        Map<String,Object> responseData = new HashMap<>();
        if(ex instanceof BusinessException){
            BusinessException businessException = (BusinessException)ex;
            responseData.put("errCode",businessException.getErrCode());
            responseData.put("errMsg",businessException.getErrMsg());
        }else{
            responseData.put("errCode", EmBusinessError.UNKNOW_ERROR.getErrCode());
            responseData.put("errMsg",EmBusinessError.UNKNOW_ERROR.getErrMsg());
        }
        return CommonReturnType.create(responseData,"fail");


    }

}