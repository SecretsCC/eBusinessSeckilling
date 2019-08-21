package com.seckillingProject.service.impl;

import com.seckillingProject.dao.UserDOMapper;
import com.seckillingProject.dao.UserPasswordDOMapper;
import com.seckillingProject.dataobject.UserDO;
import com.seckillingProject.dataobject.UserPasswordDO;
import com.seckillingProject.error.BusinessException;
import com.seckillingProject.error.EmBusinessError;
import com.seckillingProject.service.UserService;
import com.seckillingProject.service.model.UserModel;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Override
    @Transactional
    public void register(UserModel userModel) throws BusinessException {
        if(userModel == null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        if(StringUtils.isEmpty(userModel.getName())
                || userModel.getGender() == null
                || userModel.getAge() == null
                || StringUtils.isEmpty(userModel.getTelephone())){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }

        //implement model -> dataobject
        UserDO userDO = convertFromModel(userModel);
        userDOMapper.insertSelective(userDO);

        UserPasswordDO userPasswordDO = convertPasswordFromModel(userModel);
        userPasswordDOMapper.insertSelective(userPasswordDO);

        return;


    }

    private UserPasswordDO convertPasswordFromModel(UserModel userModel){
        if(userModel == null){
            return null;
        }
        UserPasswordDO userPasswordDO = new UserPasswordDO();
        userPasswordDO.setEncryptPassword(userModel.getEncryptPassWord());
        userPasswordDO.setUserId(userModel.getId());
        return userPasswordDO;
    }

    private UserDO convertFromModel(UserModel userModel){
        if(userModel == null){
            return null;
        }
        UserDO userDO = new UserDO();
        BeanUtils.copyProperties(userModel,userDO);

        return userDO;
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
