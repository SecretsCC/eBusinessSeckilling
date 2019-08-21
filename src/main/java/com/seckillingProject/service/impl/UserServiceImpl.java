package com.seckillingProject.service.impl;

import com.seckillingProject.dao.UserDOMapper;
import com.seckillingProject.dao.UserPasswordDOMapper;
import com.seckillingProject.dataobject.UserDO;
import com.seckillingProject.dataobject.UserPasswordDO;
import com.seckillingProject.service.UserService;
import com.seckillingProject.service.model.UserModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDOMapper userDOMapper;

    @Autowired
    private UserPasswordDOMapper userPasswordDOMapper;

    @Override
    public UserModel getUserById(Integer id) {
        //call userDOMapper get corresponding user data object
        UserDO userDO = userDOMapper.selectByPrimaryKey(id);

        if(userDO == null) {
            return null;
        }
        //get encrypt passcode by user_id
        UserPasswordDO userPasswordDO = userPasswordDOMapper.selectByUserId(userDO.getId());

        return convertFromDataObject(userDO, userPasswordDO);
    }

    private UserModel convertFromDataObject(UserDO userDo, UserPasswordDO userPasswordDO){
        if(userDo == null){
            return null;
        }
        UserModel userModel = new UserModel();
        BeanUtils.copyProperties(userDo,userModel);
        if(userPasswordDO != null) {
            userModel.setEncryptPassWord(userPasswordDO.getEncryptPassword());
        }


        return userModel;
    }

}
