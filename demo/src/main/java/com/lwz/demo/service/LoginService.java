package com.lwz.demo.service;

import com.lwz.demo.mapper.LoginMapperImp;
import com.lwz.demo.pojo.UserMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
@Service
public class LoginService {

    @Autowired
    LoginMapperImp loginMapper;
    public List<Integer> selectAllAdminId() {
       return loginMapper.selectAllAdminId();
    }

    public int selectUsername(String username, Integer adminId) throws SQLException {
            return loginMapper.selectUsername(username, adminId);
    }

    public void insertUserMsg(UserMsg userMsg) {
         loginMapper.insertUserMsg(userMsg);
    }

    public List<String> selectAllUsername(){
        return loginMapper.selectAllUsername();
    }
    public UserMsg selectUserMsgByUsernameAndAdminID(String username,int adminId){
        return loginMapper.selectUserMsgByNameAndAdminID(username,adminId);
    }

    public UserMsg selectUserMsgByID(int id) {
        return loginMapper.selectUserMsgByID(id);
    }

    public UserMsg selectUserMsgByUserID(int userID) {
        return loginMapper.selectUserMsgByUserID(userID);
    }
    public UserMsg selectUserMsgByUserNameAndPasswordAndAdminID(String username, String password, Integer admin_id){
        return loginMapper.selectUserMsgByUserNameAndPasswordAndAdminID(username, password, admin_id);
    }

    public void updatePassByNameAndAdminId(String s, String username, Integer admin_id) {
        loginMapper.updatePassByNameAndAdminId(s,username,admin_id);
    }

    public String selectEmailByAdminAndAdminId(Integer admin_id) {
        return loginMapper.selectEmailByAdminAndAdminId(admin_id);
    }

    public void deleteFileByNameAndUserID(String fileName, Integer userId) {
        loginMapper.deleteFileByNameAndUserID(fileName,userId);
    }

    public void deleteFileByNameAndAdminId(String fileName, Integer adminId) {
        loginMapper.deleteFileByNameAndAdminId(fileName,adminId);

    }

    public void deleteAllFileByName(String fileName){
        loginMapper.deleteAllFileByName(fileName);
    }



































































































































}
