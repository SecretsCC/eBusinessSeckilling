package com.seckillingProject.controller;

import com.seckillingProject.controller.viewobject.UserVO;
import com.seckillingProject.error.BusinessException;
import com.seckillingProject.error.EmBusinessError;
import com.seckillingProject.response.CommonReturnType;
import com.seckillingProject.service.UserService;
import com.seckillingProject.service.model.UserModel;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.security.MD5Encoder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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

    @RequestMapping(value="/login",method = {RequestMethod.POST},consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType login(@RequestParam(name="telephone")String telephone,
                                  @RequestParam(name="password")String password) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException {
        //verify
        if(org.apache.commons.lang3.StringUtils.isEmpty(telephone)){
            StringUtils.isEmpty(password);
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }

        //login service, test if login is legal
        UserModel userModel = userService.validateLogin(telephone,this.EncodeByMd5(password));

        //add to user login successful session
        this.httpServletRequest.getSession().setAttribute("IS_LOGIN",true);
        this.httpServletRequest.getSession().setAttribute("LOGIN_USER",userModel);

        return CommonReturnType.create(null);
    }

    //user register page
    @RequestMapping(value="/register",method = {RequestMethod.POST},consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType register(@RequestParam(name="telephone")String telephone,
                                     @RequestParam(name="otpCode")String otpCode,
                                     @RequestParam(name="name")String name,
                                     @RequestParam(name="gender")Integer gender,
                                     @RequestParam(name="age")Integer age,
                                     @RequestParam(name="password")String password) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException {
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
        userModel.setEncryptPassWord(this.EncodeByMd5(password));

        userService.register(userModel);
        return CommonReturnType.create(null);


    }

    public String EncodeByMd5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        //make a method
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        BASE64Encoder basee64en = new BASE64Encoder();
        //encode
        String newstr = basee64en.encode(md5.digest(str.getBytes("utf-8")));
        return newstr;
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
