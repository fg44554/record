package com.lwz.demo.mapper;

import com.lwz.demo.pojo.Msg;
import com.lwz.demo.pojo.Text;
import com.lwz.demo.pojo.UpMsg;
import java.util.List;

public  interface MsgMapper
{
    int deleteByPrimaryKey(Integer paramInteger);

    int insert(Msg paramMsg);

    List<Msg> selectAll();

    int updateByPrimaryKey(Msg paramMsg);

    UpMsg selectByName(String paramString);

    int insertUpMsg(UpMsg paramUpMsg);

    List<UpMsg> selectAllUp();

    int insertT(Text paramText);

    List<Text> selectAllTextByName(String paramString);


    void insertPhoneByText(String paramString1, String paramString2);


    int updateTimes(int paramInt);


    List<Integer> selectUidByName(String paramString);


    int selectIdCount();


    List<String> selectSizeByName(String paramString);


    List<UpMsg> selectByStat();


    List<Integer> selectIdByMoodStat();


    int updateStatById(int paramInt);


    int updateMoodStatById(int paramInt);


    int updateMoodById(int paramInt1, int paramInt2);


    Text selectTextById(int paramInt);


    List<UpMsg> selectAllAdminUpByAdminID(Integer paramInteger);


    List<UpMsg> selectAllAdminUpByID(int paramInt);


    List<String> selectFileNameByUserID(Integer paramInteger);


    UpMsg selectByNameAndAdminId(String paramString, Integer paramInteger);


    UpMsg selectByNameAndUserId(String paramString, Integer paramInteger);


    List<Text> selectAllTextByID(int paramInt);

    String selectTextByLastID(int paramInt);

    List<String> selectTextByUID(int paramInt);
}
