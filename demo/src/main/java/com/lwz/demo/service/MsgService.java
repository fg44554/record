package com.lwz.demo.service;

import com.lwz.demo.mapper.MsgMapper;
import com.lwz.demo.mapper.MsgMapperImp;
import com.lwz.demo.pojo.Msg;
import com.lwz.demo.pojo.Text;
import com.lwz.demo.pojo.UpMsg;
import java.io.PrintStream;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class MsgService
{
    public List<Msg> selectAll()
    {
        MsgMapper msgMapper = new MsgMapperImp();
        return msgMapper.selectAll();
    }

    public int updateByPrimaryKey(Msg record)
    {
        MsgMapper msgMapper = new MsgMapperImp();
        return 0;
    }

    public UpMsg selectByName(String name)
    {
        MsgMapper msgMapper = new MsgMapperImp();
        return msgMapper.selectByName(name);
    }

    public int insertUpMsg(UpMsg upMsg)
    {
        MsgMapper msgMapper = new MsgMapperImp();
        return msgMapper.insertUpMsg(upMsg);
    }

    public List<UpMsg> selectAllUp()
    {
        MsgMapper msgMapper = new MsgMapperImp();
        return msgMapper.selectAllUp();
    }

    public int insertT(Text t)
    {
        MsgMapper msgMapper = new MsgMapperImp();
        return msgMapper.insertT(t);
    }

    public List<Text> selectAllTextByName(String name)
    {
        MsgMapper msgMapper = new MsgMapperImp();
        return msgMapper.selectAllTextByName(name);
    }

    public void insertPhoneByText(String phone, String text)
    {
        MsgMapper msgMapper = new MsgMapperImp();
        msgMapper.insertPhoneByText(phone, text);
    }

    public int updateTimes(int uid)
    {
        MsgMapper msgMapper = new MsgMapperImp();
        return msgMapper.updateTimes(uid);
    }

    public List<Integer> selectUidByName(String name)
    {
        MsgMapper msgMapper = new MsgMapperImp();
        return msgMapper.selectUidByName(name);
    }

    public int selectIdCount()
    {
        MsgMapper msgMapper = new MsgMapperImp();
        return msgMapper.selectIdCount();
    }

    public List<String> selectSizeByName(String name)
    {
        MsgMapper msgMapper = new MsgMapperImp();
        return msgMapper.selectSizeByName(name);
    }

    public List<UpMsg> selectByStat()
    {
        MsgMapper msgMapper = new MsgMapperImp();
        return msgMapper.selectByStat();
    }

    public List<Integer> selectIdByMoodStat()
    {
        MsgMapper msgMapper = new MsgMapperImp();
        return msgMapper.selectIdByMoodStat();
    }

    public int updateStatById(int id)
    {
        MsgMapper msgMapper = new MsgMapperImp();
        return msgMapper.updateStatById(id);
    }

    public int updateMoodStatById(int id)
    {
        MsgMapper msgMapper = new MsgMapperImp();
        return msgMapper.updateMoodStatById(id);
    }

    public int updateMoodById(int id, int mood)
    {
        MsgMapper msgMapper = new MsgMapperImp();
        return msgMapper.updateMoodById(id, mood);
    }

    public Text selectTextById(int id)
    {
        MsgMapper msgMapper = new MsgMapperImp();
        return msgMapper.selectTextById(id);
    }

    public int deleteByPrimaryKey(Integer id)
    {
        return 0;
    }

    public int insert(Msg i)
    {
        System.out.println(i);
        MsgMapper msgMapper = new MsgMapperImp();
        return msgMapper.insert(i);
    }

    public List<UpMsg> selectAllAdminUpByAdminID(Integer admin_id)
    {
        MsgMapper msgMapper = new MsgMapperImp();
        System.out.println("����List������" + msgMapper.selectAllAdminUpByAdminID(admin_id).size());
        for (int i = 0; i < msgMapper.selectAllAdminUpByAdminID(admin_id).size(); i++) {
            System.out.println("������SERVICE������������������ID������beanList" + msgMapper.selectAllAdminUpByAdminID(admin_id));
        }
        return msgMapper.selectAllAdminUpByAdminID(admin_id);
    }

    public List<UpMsg> selectAllAdminUpByID(int id)
    {
        MsgMapper msgMapper = new MsgMapperImp();return msgMapper.selectAllAdminUpByID(id);
    }

    public List<String> selectFileNameByUserID(Integer id)
    {
        MsgMapper msgMapper = new MsgMapperImp();
        return msgMapper.selectFileNameByUserID(id);
    }

    public UpMsg selectByNameAndAdminId(String name, Integer admin_id)
    {
        System.out.println("������SERVICE��");
        MsgMapper msgMapper = new MsgMapperImp();
        return msgMapper.selectByNameAndAdminId(name, admin_id);
    }

    public UpMsg selectByNameAndUserId(String name, Integer userID)
    {
        MsgMapper msgMapper = new MsgMapperImp();
        return msgMapper.selectByNameAndUserId(name, userID);
    }

    public List<Text> selectAllTextByID(int id)
    {
        MsgMapper msgMapper = new MsgMapperImp();
        return msgMapper.selectAllTextByID(id);
    }

    public String selectTextByLastID(int i)
    {
        MsgMapper msgMapper = new MsgMapperImp();
        return msgMapper.selectTextByLastID(i);
    }

    public List<String> selectTextByUID(int uid)
    {
        MsgMapper msgMapper = new MsgMapperImp();
        return msgMapper.selectTextByUID(uid);
    }
}
