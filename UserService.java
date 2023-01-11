package com.mayikt.service;

import com.mayikt.dao.UserDao;
import com.mayikt.entity.UserEntity;

public class UserService {
    private UserDao userDao = new UserDao();

    /**
     * register user service
     * @param userEntity
     * @return
     */
    public int Register(UserEntity userEntity){
        UserEntity user1 = userDao.getUserByPhone(userEntity.getPhone());
        if(user1==null){
            int result = userDao.registerUser(userEntity);
            return result;
        }else{
            System.out.println("this phone number already registered");
            return 0;
        }
    }
    public UserEntity login(UserEntity userEntity){
        return userDao.login(userEntity);
    }
}
