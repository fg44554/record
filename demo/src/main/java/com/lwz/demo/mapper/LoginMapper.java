package com.lwz.demo.mapper;

import com.lwz.demo.pojo.UserMsg;
import java.sql.SQLException;
import java.util.List;

public  interface LoginMapper
{
      List<Integer> selectAllAdminId();

      int selectUsername(String paramString, Integer paramInteger)
            throws SQLException;

      List<String> selectAllUsername();

      void insertUserMsg(UserMsg paramUserMsg);

      UserMsg selectUserMsgByNameAndAdminID(String paramString, Integer paramInteger);

      UserMsg selectUserMsgByID(int paramInt);

      UserMsg selectUserMsgByUserID(int paramInt);

      UserMsg selectUserMsgByUserNameAndPasswordAndAdminID(String paramString1, String paramString2, Integer paramInteger);

      String selectEmailByAdminAndAdminId(Integer paramInteger);

      void updatePassByNameAndAdminId(String paramString1, String paramString2, Integer paramInteger);

      void deleteAllFileByName(String paramString);

      void deleteFileByNameAndUserID(String paramString, Integer paramInteger);

      void deleteFileByNameAndAdminId(String paramString, Integer paramInteger);

      void updateUserPassToMD5(String paramString, int paramInt);

      List<UserMsg> selectAll();
}
