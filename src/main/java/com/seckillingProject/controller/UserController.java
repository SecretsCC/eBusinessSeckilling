package com.seckillingProject.controller;

import com.seckillingProject.controller.viewobject.UserVO;
import com.seckillingProject.error.BusinessException;
import com.seckillingProject.error.EmBusinessError;
import com.seckillingProject.response.CommonReturnType;
import com.seckillingProject.service.UserService;
import com.seckillingProject.service.model.UserModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller("user")
@RequestMapping("/user")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

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
