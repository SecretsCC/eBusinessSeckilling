package com.seckillingProject.validator;

import org.apache.commons.lang3.StringUtils;

import java.util.Map;

public class ValidationResult {
    //verify result
    private boolean hasErrors;

    //error map
    private Map<String,String> errorMsgMap;



    public boolean isHasErrors() {
        return hasErrors;
    }

    public void setHasErrors(boolean hasErrors) {
        this.hasErrors = hasErrors;
    }

    public Map<String, String> getErrorMsgMap() {
        return errorMsgMap;
    }

    public void setErrorMsgMap(Map<String, String> errorMsgMap) {
        this.errorMsgMap = errorMsgMap;
    }

    //get error message
    public String getErrMsg(){
        return StringUtils.join(errorMsgMap.values().toArray(),",");
    }
}
