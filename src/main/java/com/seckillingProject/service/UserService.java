package com.seckillingProject.service;

import com.seckillingProject.error.BusinessException;
import com.seckillingProject.service.model.UserModel;

public interface UserService {
    //get user object by user id
    UserModel getUserById(Integer id);
    void register(UserModel userModel) throws BusinessException;



    UserModel validateLogin(String telephone, String enCryptPassword) throws BusinessException;
}
