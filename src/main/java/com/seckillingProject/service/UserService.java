package com.seckillingProject.service;

import com.seckillingProject.service.model.UserModel;

public interface UserService {
    //get user object by user id
    UserModel getUserById(Integer id);
}
