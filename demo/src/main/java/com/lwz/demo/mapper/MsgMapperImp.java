package com.lwz.demo.mapper;

import com.lwz.demo.pojo.Msg;
import com.lwz.demo.pojo.Text;
import com.lwz.demo.pojo.UpMsg;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import java.io.PrintStream;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MsgMapperImp implements MsgMapper
{
    Connection con;
    PreparedStatement pstmt;
    ResultSet rs;

    @Override
    public int deleteByPrimaryKey(Integer id)
    {
        return 0;
    }

    @Override
    public int insert(Msg u)
    {
        int result = 0;
        String sql = "insert into msg(name,gender,age,id,birth) values(?,?,?,?,?)";
        try
        {
            this.con = getConnection();
            this.pstmt = ((PreparedStatement)this.con.prepareStatement(sql));

            this.pstmt.setString(1, u.getName());
            this.pstmt.setString(2, u.getGender());
            this.pstmt.setInt(3, u.getAge().intValue());
            this.pstmt.setInt(4, u.getId().intValue());
            this.pstmt.setInt(5, u.getBirth().intValue());
            result = this.pstmt.executeUpdate();
            if (result != 0) {
                System.out.println("��������������������");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            closeAll(this.con, this.pstmt, this.rs);
        }
        return result;
    }

    @Override
    public int insertUpMsg(UpMsg u)
    {
        int result = 0;
        String sql = "insert into upmsg(name,size,type,stat,admin_id,user_id) values(?,?,?,?,?,?)";
        try
        {
            this.con = getConnection();
            this.pstmt = ((PreparedStatement)this.con.prepareStatement(sql));

            this.pstmt.setString(1, u.getName());
            this.pstmt.setString(2, u.getSize());

            this.pstmt.setString(3, u.getType());
            this.pstmt.setString(4, u.getStat());
            this.pstmt.setInt(5, u.getAdmin_id());
            this.pstmt.setInt(6, u.getUser_id());
            result = this.pstmt.executeUpdate();
            if (result != 0) {
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            closeAll(this.con, this.pstmt, this.rs);
        }
        return result;
    }

    @Override
    public List<UpMsg> selectAllUp()
    {
        List<UpMsg> list = new ArrayList();
        String sql = "select * from upmsg ";
        try
        {
            this.con = getConnection();
            this.pstmt = ((PreparedStatement)this.con.prepareStatement(sql));
            this.rs = this.pstmt.executeQuery();
            UpMsg u = null;
            while (this.rs.next())
            {
                u = new UpMsg();
                u.setId(Integer.valueOf(this.rs.getInt("id")));
                u.setName(this.rs.getString("name"));
                u.setSize(this.rs.getString("size"));
                u.setTime(this.rs.getString("time"));
                u.setType(this.rs.getString("type"));
                u.setUser_id(Integer.valueOf(this.rs.getInt("user_id")));
                u.setAdmin_id(Integer.valueOf(this.rs.getInt("admin_id")));
                list.add(u);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            closeAll(this.con, this.pstmt, this.rs);
        }
        return list;
    }

    @Override
    public int insertT(Text t)
    {
        int result = 0;
        String sql = "insert into text(title,text,b_time,e_time,time,uid,phone,moodstat,user_id,admin_id) values(?,?,?,?,?,?,?,?,?,?)";
        try
        {
            this.con = getConnection();
            this.pstmt = ((PreparedStatement)this.con.prepareStatement(sql));

            this.pstmt.setString(1, t.getTitle());
            this.pstmt.setString(2, t.getText());
            this.pstmt.setInt(3, t.getbTime());
            this.pstmt.setInt(4, t.geteTime());
            this.pstmt.setInt(5, t.getTime());
            this.pstmt.setInt(6, t.getUid());
            this.pstmt.setString(7, t.getPhone());
            this.pstmt.setString(8, t.getMood_stat());
            this.pstmt.setInt(9, t.getUser_id());
            this.pstmt.setInt(10, t.getAdmin_id());
            result = this.pstmt.executeUpdate();
            if (result != 0) {
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            closeAll(this.con, this.pstmt, this.rs);
        }
        return result;
    }

    @Override
    public List<Text> selectAllTextByName(String name)
    {
        List<Text> list = new ArrayList();
        String sql = "select * from text where title='" + name + "' group by text order by b_time asc ";
        try
        {
            this.con = getConnection();
            this.pstmt = ((PreparedStatement)this.con.prepareStatement(sql));
            this.rs = this.pstmt.executeQuery();
            Text u = null;
            while (this.rs.next())
            {
                u = new Text();
                u.setTitle(this.rs.getString("title"));
                u.setText(this.rs.getString("text"));
                u.setTime(this.rs.getInt("time"));
                u.setbTime(this.rs.getInt("b_time"));
                u.seteTime(this.rs.getInt("e_time"));
                u.setPhone(this.rs.getString("phone"));
                u.setTimes(this.rs.getInt("times"));
                u.setMood(this.rs.getInt("mood"));
                list.add(u);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            closeAll(this.con, this.pstmt, this.rs);
        }
        return list;
    }

    @Override
    public void insertPhoneByText(String phone, String text)
    {
        int result = 0;
        String sql = "insert into text (phone) select (?) from dual where exists(select text from text where text='" + text + "')";
        try
        {
            this.con = getConnection();
            this.pstmt = ((PreparedStatement)this.con.prepareStatement(sql));

            this.pstmt.setString(1, phone);

            result = this.pstmt.executeUpdate();
            if (result != 0) {
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            closeAll(this.con, this.pstmt, this.rs);
        }
    }

    @Override
    public int updateTimes(int uid)
    {
        int count = 0;
        String sql = "update text,times_v3 set text.times=times_v3.times where times_v3.phone=text.phone and times_v3.uid=" + uid;
        try
        {
            this.con = getConnection();
            this.pstmt = ((PreparedStatement)this.con.prepareStatement(sql));
            count = this.pstmt.executeUpdate();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            closeAll(this.con, this.pstmt, this.rs);
        }
        return count;
    }

    @Override
    public List<Integer> selectUidByName(String name)
    {
        List<Integer> list = new ArrayList();
        String sql = "select uid from text where title='" + name + "'";
        try
        {
            this.con = getConnection();
            this.pstmt = ((PreparedStatement)this.con.prepareStatement(sql));
            this.rs = this.pstmt.executeQuery();
            while (this.rs.next()) {
                list.add(Integer.valueOf(this.rs.getInt("uid")));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            closeAll(this.con, this.pstmt, this.rs);
        }
        return list;
    }

    @Override
    public int selectIdCount()
    {
        int count = 0;
        String sql = "select count(id) as count from text ";
        try
        {
            this.con = getConnection();
            this.pstmt = ((PreparedStatement)this.con.prepareStatement(sql));
            this.rs = this.pstmt.executeQuery();
            if (this.rs.next())
            {
                count = this.rs.getInt("count");
                System.out.println(count);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            closeAll(this.con, this.pstmt, this.rs);
        }
        System.out.println(count);
        return count;
    }

    @Override
    public List<String> selectSizeByName(String name)
    {
        List<String> sizes = null;
        String sql = "select size from upmsg where name='" + name + "'";
        try
        {
            this.con = getConnection();
            this.pstmt = ((PreparedStatement)this.con.prepareStatement(sql));
            this.rs = this.pstmt.executeQuery();
            while (this.rs.next()) {
                sizes.add(this.rs.getString("size"));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            closeAll(this.con, this.pstmt, this.rs);
        }
        return sizes;
    }

    @Override
    public List<UpMsg> selectByStat()
    {
        List<UpMsg> list = new ArrayList();
        String sql = "select * from upmsg where stat='n'";
        try
        {
            this.con = getConnection();
            this.pstmt = ((PreparedStatement)this.con.prepareStatement(sql));
            this.rs = this.pstmt.executeQuery();
            UpMsg u = null;
            while (this.rs.next())
            {
                u = new UpMsg();
                u.setId(Integer.valueOf(this.rs.getInt("id")));
                u.setName(this.rs.getString("name"));
                u.setSize(this.rs.getString("size"));
                u.setTime(this.rs.getString("time"));
                u.setType(this.rs.getString("type"));
                u.setStat(this.rs.getString("stat"));
                u.setAdmin_id(Integer.valueOf(this.rs.getInt("admin_id")));
                u.setUser_id(Integer.valueOf(this.rs.getInt("user_id")));
                list.add(u);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            closeAll(this.con, this.pstmt, this.rs);
        }
        return list;
    }

    @Override
    public List<Integer> selectIdByMoodStat()
    {
        List<Integer> list = new ArrayList();
        String sql = "select id from text where moodstat='n'";
        try
        {
            this.con = getConnection();
            this.pstmt = ((PreparedStatement)this.con.prepareStatement(sql));
            this.rs = this.pstmt.executeQuery();
            Text u = null;
            while (this.rs.next())
            {
                u = new Text();
                u.setId(this.rs.getInt("id"));
                list.add(Integer.valueOf(u.getId()));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            closeAll(this.con, this.pstmt, this.rs);
        }
        return list;
    }

    @Override
    public int updateStatById(int id)
    {
        int count = 0;
        String sql = "update upmsg set stat='Y' where id=" + id;
        try
        {
            this.con = getConnection();
            this.pstmt = ((PreparedStatement)this.con.prepareStatement(sql));
            count = this.pstmt.executeUpdate();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            closeAll(this.con, this.pstmt, this.rs);
        }
        return count;
    }

    @Override
    public int updateMoodStatById(int id)
    {
        int count = 0;
        String sql = "update text set moodstat='Y' where id=" + id;
        try
        {
            this.con = getConnection();
            this.pstmt = ((PreparedStatement)this.con.prepareStatement(sql));
            count = this.pstmt.executeUpdate();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            closeAll(this.con, this.pstmt, this.rs);
        }
        return count;
    }

    @Override
    public int updateMoodById(int id, int mood)
    {
        int count = 0;
        String sql = "update text set mood=" + mood + " where id=" + id;
        try
        {
            this.con = getConnection();
            this.pstmt = ((PreparedStatement)this.con.prepareStatement(sql));
            count = this.pstmt.executeUpdate();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            closeAll(this.con, this.pstmt, this.rs);
        }
        return count;
    }

    @Override
    public Text selectTextById(int id)
    {
        Text u = null;
        String sql = "select * from text where id=" + id;
        try
        {
            this.con = getConnection();
            this.pstmt = ((PreparedStatement)this.con.prepareStatement(sql));
            this.rs = this.pstmt.executeQuery();
            if (this.rs.next())
            {
                u = new Text();
                u.setId(this.rs.getInt("id"));
                u.setTitle(this.rs.getString("title"));
                u.setText(this.rs.getString("text"));
                u.setTime(this.rs.getInt("time"));
                u.setbTime(this.rs.getInt("b_time"));
                u.seteTime(this.rs.getInt("e_time"));
                u.setPhone(this.rs.getString("phone"));
                u.setUid(this.rs.getInt("uid"));
                u.setTimes(this.rs.getInt("times"));
                u.setMood_stat(this.rs.getString("moodstat"));
                u.setMood(this.rs.getInt("mood"));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            closeAll(this.con, this.pstmt, this.rs);
        }
        return u;
    }

    @Override
    public List<UpMsg> selectAllAdminUpByAdminID(Integer admin_id)
    {
        List<UpMsg> list = new ArrayList();
        String sql = "select * from upmsg where admin_id=" + admin_id;
        try
        {
            this.con = getConnection();
            this.pstmt = ((PreparedStatement)this.con.prepareStatement(sql));
            this.rs = this.pstmt.executeQuery();
            UpMsg u = null;
            while (this.rs.next())
            {
                u = new UpMsg();
                u.setId(Integer.valueOf(this.rs.getInt("id")));
                u.setName(this.rs.getString("name"));
                u.setSize(this.rs.getString("size"));
                u.setTime(this.rs.getString("time"));
                u.setType(this.rs.getString("type"));
                u.setUser_id(Integer.valueOf(this.rs.getInt("user_id")));
                u.setAdmin_id(Integer.valueOf(this.rs.getInt("admin_id")));
                list.add(u);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            closeAll(this.con, this.pstmt, this.rs);
        }
        return list;
    }

    @Override
    public List<UpMsg> selectAllAdminUpByID(int id)
    {
        List<UpMsg> list = new ArrayList();
        String sql = "select * from upmsg where user_id=" + id;
        try
        {
            this.con = getConnection();
            this.pstmt = ((PreparedStatement)this.con.prepareStatement(sql));
            this.rs = this.pstmt.executeQuery();
            UpMsg u = null;
            while (this.rs.next())
            {
                u = new UpMsg();
                u.setId(Integer.valueOf(this.rs.getInt("id")));
                u.setName(this.rs.getString("name"));
                u.setSize(this.rs.getString("size"));
                u.setTime(this.rs.getString("time"));
                u.setType(this.rs.getString("type"));
                u.setUser_id(Integer.valueOf(this.rs.getInt("user_id")));
                u.setAdmin_id(Integer.valueOf(this.rs.getInt("admin_id")));
                list.add(u);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            closeAll(this.con, this.pstmt, this.rs);
        }
        return list;
    }

    @Override
    public List<String> selectFileNameByUserID(Integer id)
    {
        List<String> list = new ArrayList();
        String sql = "select name from upmsg where user_id=" + id;
        try
        {
            this.con = getConnection();
            this.pstmt = ((PreparedStatement)this.con.prepareStatement(sql));
            this.rs = this.pstmt.executeQuery();
            while (this.rs.next())
            {
                String name = this.rs.getString("name");
                list.add(name);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            closeAll(this.con, this.pstmt, this.rs);
        }
        return list;
    }

    @Override
    public UpMsg selectByNameAndAdminId(String name, Integer admin_id)
    {
        UpMsg u = new UpMsg();
        String sql = "select * from upmsg where name='" + name + "' and admin_id=" + admin_id;
        try
        {
            this.con = getConnection();
            this.pstmt = ((PreparedStatement)this.con.prepareStatement(sql));
            this.rs = this.pstmt.executeQuery();
            u = new UpMsg();
            if (this.rs.next())
            {
                u.setName(this.rs.getString("name"));
                u.setType(this.rs.getString("type"));
                u.setTime(this.rs.getString("time"));
                u.setSize(this.rs.getString("size"));
                u.setId(Integer.valueOf(this.rs.getInt("id")));
                u.setUser_id(Integer.valueOf(this.rs.getInt("user_id")));
                u.setAdmin_id(Integer.valueOf(this.rs.getInt("admin_id")));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            closeAll(this.con, this.pstmt, this.rs);
        }
        return u;
    }

    @Override
    public UpMsg selectByNameAndUserId(String name, Integer userID)
    {
        UpMsg u = new UpMsg();
        String sql = "select * from upmsg where name='" + name + "' and user_id=" + userID;
        try
        {
            this.con = getConnection();
            this.pstmt = ((PreparedStatement)this.con.prepareStatement(sql));
            this.rs = this.pstmt.executeQuery();
            u = new UpMsg();
            if (this.rs.next())
            {
                u.setName(this.rs.getString("name"));
                u.setType(this.rs.getString("type"));
                u.setTime(this.rs.getString("time"));
                u.setSize(this.rs.getString("size"));
                u.setId(Integer.valueOf(this.rs.getInt("id")));
                u.setUser_id(Integer.valueOf(this.rs.getInt("user_id")));
                u.setAdmin_id(Integer.valueOf(this.rs.getInt("admin_id")));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            closeAll(this.con, this.pstmt, this.rs);
        }
        return u;
    }

    @Override
    public List<Text> selectAllTextByID(int id)
    {
        List<Text> list = new ArrayList();
        String sql = "select * from text where uid=" + id;
        try
        {
            this.con = getConnection();
            this.pstmt = ((PreparedStatement)this.con.prepareStatement(sql));
            this.rs = this.pstmt.executeQuery();
            Text u = null;
            while (this.rs.next())
            {
                u = new Text();
                u.setId(this.rs.getInt("id"));
                u.setTitle(this.rs.getString("title"));
                u.setText(this.rs.getString("text"));
                u.setTime(this.rs.getInt("time"));
                u.setbTime(this.rs.getInt("b_time"));
                u.seteTime(this.rs.getInt("e_time"));
                u.setPhone(this.rs.getString("phone"));
                u.setUid(this.rs.getInt("uid"));
                u.setTimes(this.rs.getInt("times"));
                u.setMood_stat(this.rs.getString("moodstat"));
                u.setMood(this.rs.getInt("mood"));
                list.add(u);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            closeAll(this.con, this.pstmt, this.rs);
        }
        return list;
    }

    @Override
    public String selectTextByLastID(int i)
    {
        String sql = "select text from text where id=" + i;
        try
        {
            this.con = getConnection();
            this.pstmt = ((PreparedStatement)this.con.prepareStatement(sql));
            this.rs = this.pstmt.executeQuery();
            String text = null;
            if (this.rs.next())
            {
                text = this.rs.getString("text");
                return text;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            closeAll(this.con, this.pstmt, this.rs);
        }
        return "";
    }

    @Override
    public List<String> selectTextByUID(int uid)
    {
        List<String> list = new ArrayList();
        String sql = "select text from text where uid=" + uid;
        try
        {
            this.con = getConnection();
            this.pstmt = ((PreparedStatement)this.con.prepareStatement(sql));
            this.rs = this.pstmt.executeQuery();
            while (this.rs.next())
            {
                String text = this.rs.getString("text");
                list.add(text.trim());
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            closeAll(this.con, this.pstmt, this.rs);
        }
        return list;
    }

    private Connection getConnection()
    {
        Connection conn = null;
        try
        {
            Class.forName("com.mysql.jdbc.Driver");

            conn = (Connection)DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/lv?useSSL=false&useUnicode=true&characterEncoding=UTF-8", "root", "lwz123456");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return conn;
    }

    public void closeAll(Connection conn, PreparedStatement pstmt, ResultSet rs)
    {
        if (rs != null) {
            try
            {
                rs.close();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        if (pstmt != null) {
            try
            {
                pstmt.close();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        if (conn != null) {
            try
            {
                conn.close();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<Msg> selectAll()
    {
        List<Msg> list = new ArrayList();
        String sql = "select * from msg order by id";
        try
        {
            this.con = getConnection();
            this.pstmt = ((PreparedStatement)this.con.prepareStatement(sql));
            this.rs = this.pstmt.executeQuery();
            Msg u = null;
            while (this.rs.next())
            {
                u = new Msg();
                u.setId(Integer.valueOf(this.rs.getInt("id")));
                u.setName(this.rs.getString("name"));
                u.setGender(this.rs.getString("gender"));
                u.setAge(Integer.valueOf(this.rs.getInt("age")));
                u.setBirth(Integer.valueOf(this.rs.getInt("birth")));
                list.add(u);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            closeAll(this.con, this.pstmt, this.rs);
        }
        return list;
    }

    @Override
    public int updateByPrimaryKey(Msg record)
    {
        return 0;
    }

    @Override
    public UpMsg selectByName(String name)
    {
        UpMsg u = null;
        String sql = "select * from upmsg where name='" + name + "'";
        try
        {
            this.con = getConnection();
            this.pstmt = ((PreparedStatement)this.con.prepareStatement(sql));
            this.rs = this.pstmt.executeQuery();
            u = new UpMsg();
            if (this.rs.next())
            {
                u.setName(this.rs.getString("name"));
                u.setType(this.rs.getString("type"));
                u.setTime(this.rs.getString("time"));
                u.setSize(this.rs.getString("size"));
                u.setId(Integer.valueOf(this.rs.getInt("id")));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            closeAll(this.con, this.pstmt, this.rs);
        }
        return u;
    }
}
