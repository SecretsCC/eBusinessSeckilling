package com.seckillingProject.controller;

import com.seckillingProject.controller.viewobject.UserVO;
import com.seckillingProject.error.BusinessException;
import com.seckillingProject.error.EmBusinessError;
import com.seckillingProject.response.CommonReturnType;
import com.seckillingProject.service.UserService;
import com.seckillingProject.service.model.UserModel;
import org.apache.tomcat.util.security.MD5Encoder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Controller("user")
@RequestMapping("/user")
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private HttpServletRequest httpServletRequest;

    //user register page
    @RequestMapping(value="/register",method = {RequestMethod.POST},consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType register(@RequestParam(name="telphone")String telephone,
                                     @RequestParam(name="otpCode")String otpCode,
                                     @RequestParam(name="name")String name,
                                     @RequestParam(name="gender")Integer gender,
                                     @RequestParam(name="age")Integer age,
                                     @RequestParam(name="password")String password) throws BusinessException {
        //verify otpCode is right
        String inSessionotpCode = (String)this.httpServletRequest.getSession().getAttribute(telephone);
        if(!com.alibaba.druid.util.StringUtils.equals(otpCode,inSessionotpCode)){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"verify code wrong");
        }
        //register new user
        UserModel userModel = new UserModel();
        userModel.setName(name);
        userModel.setGender(new Byte(String.valueOf(gender.intValue())));
        userModel.setAge(age);
        userModel.setTelephone(telephone);
        userModel.setRegisterMode("byPhone");
        userModel.setEncryptPassWord(MD5Encoder.encode(password.getBytes()));

        userService.register(userModel);
        return CommonReturnType.create(null);


    }


    //otp text API
    @RequestMapping(value="/getotp",method = {RequestMethod.POST},consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType getOtp(@RequestParam(name="telephone")String telephone){
        //follow some rule to generate otp verify code
        Random random = new Random();
        int randomInt = random.nextInt(99999);
        randomInt += 10000;
        String otpCode = String.valueOf(randomInt);

        //send otp code corelated with user's phone
        //through Http session bind phone and otp code
        httpServletRequest.getSession().setAttribute(telephone,otpCode);

        //send otp to user's phone,omit
        System.out.println("telephone = " + telephone + "& otpCode = " + otpCode);
        return CommonReturnType.create(null);
    }

    @RequestMapping("/get")
    @ResponseBody
    public CommonReturnType getUser(@RequestParam(name="id")Integer id) throws BusinessException {
        //get user object by calling service then return to front end
        UserModel userModel = userService.getUserById(id);

        //if user not found
        if(userModel == null) {
            throw new BusinessException(EmBusinessError.USER_NOT_EXIST);
        }


        //transfer usermodel to userviewobject
        UserVO userVO = converFromModel(userModel);
        return CommonReturnType.create(userVO);
    }

    private UserVO converFromModel(UserModel userModel){
        if(userModel == null){
            return null;
        }
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(userModel,userVO);
        return userVO;
    }


}
