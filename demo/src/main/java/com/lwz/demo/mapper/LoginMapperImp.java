package com.lwz.demo.mapper;

import com.lwz.demo.pojo.UserMsg;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import java.io.PrintStream;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class LoginMapperImp implements LoginMapper
{
    Connection con;
    PreparedStatement pstmt;
    ResultSet rs;

    @Override
    public List<Integer> selectAllAdminId()
    {
        List<Integer> list = new ArrayList();
        String sql = "select admin_id from usermsg";
        try
        {
            this.con = getConnection();
            this.pstmt = ((PreparedStatement)this.con.prepareStatement(sql));
            this.rs = this.pstmt.executeQuery();
            while (this.rs.next())
            {
                int admin_id = this.rs.getInt("admin_id");
                list.add(Integer.valueOf(admin_id));
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
    public int selectUsername(String username, Integer adminId)
    {
        String sql = "select username from usermsg where username='" + username + "'and admin_id=" + adminId;
        try
        {
            this.con = getConnection();
            this.pstmt = ((PreparedStatement)this.con.prepareStatement(sql));
            this.rs = this.pstmt.executeQuery();
            int i;
            if (this.rs.next()) {
                return 1;
            }
            return 0;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            closeAll(this.con, this.pstmt, this.rs);
        }
        return 0;
    }

    @Override
    public List<String> selectAllUsername()
    {
        List<String> list = new ArrayList();
        String sql = "select username from usermsg ";
        try
        {
            this.con = getConnection();
            this.pstmt = ((PreparedStatement)this.con.prepareStatement(sql));
            this.rs = this.pstmt.executeQuery();
            while (this.rs.next())
            {
                String username = this.rs.getString("username");
                list.add(username);
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
    public void insertUserMsg(UserMsg userMsg)
    {
        int result = 0;
        String sql = "insert into usermsg (username,password,admin,root,admin_id,email)values(?,?,?,?,?,?)";
        try
        {
            this.con = getConnection();
            this.pstmt = ((PreparedStatement)this.con.prepareStatement(sql));

            this.pstmt.setString(1, userMsg.getUsername());
            this.pstmt.setString(2, userMsg.getPassword());
            this.pstmt.setString(3, userMsg.getAdmin());
            this.pstmt.setString(4, userMsg.getRoot());
            this.pstmt.setInt(5, userMsg.getAdmin_id());
            this.pstmt.setString(6, userMsg.getEmail());
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
    public UserMsg selectUserMsgByNameAndAdminID(String username, Integer adminId)
    {
        UserMsg userMsg = new UserMsg();
        String sql = "select * from usermsg where username='" + username + "' and admin_id=" + adminId;
        try
        {
            this.con = getConnection();
            this.pstmt = ((PreparedStatement)this.con.prepareStatement(sql));
            this.rs = this.pstmt.executeQuery();
            while (this.rs.next())
            {
                userMsg.setId(this.rs.getInt("id"));
                userMsg.setUsername(this.rs.getString("username"));
                userMsg.setPassword(this.rs.getString("password"));
                userMsg.setAdmin(this.rs.getString("admin"));
                userMsg.setRoot(this.rs.getString("root"));
                userMsg.setAdmin_id(this.rs.getInt("admin_id"));
                userMsg.setEmail(this.rs.getString("email"));
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
        return userMsg;
    }

    @Override
    public UserMsg selectUserMsgByID(int id)
    {
        UserMsg userMsg = new UserMsg();
        String sql = "select * from usermsg where id=" + id;
        try
        {
            this.con = getConnection();
            this.pstmt = ((PreparedStatement)this.con.prepareStatement(sql));
            this.rs = this.pstmt.executeQuery();
            while (this.rs.next())
            {
                userMsg.setId(this.rs.getInt("id"));
                userMsg.setUsername(this.rs.getString("username"));
                userMsg.setPassword(this.rs.getString("password"));
                userMsg.setAdmin(this.rs.getString("admin"));
                userMsg.setRoot(this.rs.getString("root"));
                userMsg.setAdmin_id(this.rs.getInt("admin_id"));
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
        return userMsg;
    }

    @Override
    public UserMsg selectUserMsgByUserID(int userID)
    {
        UserMsg userMsg = new UserMsg();
        String sql = "select * from usermsg where id=" + userID;
        try
        {
            this.con = getConnection();
            this.pstmt = ((PreparedStatement)this.con.prepareStatement(sql));
            this.rs = this.pstmt.executeQuery();
            while (this.rs.next())
            {
                userMsg.setId(this.rs.getInt("id"));
                userMsg.setUsername(this.rs.getString("username"));
                userMsg.setPassword(this.rs.getString("password"));
                userMsg.setAdmin(this.rs.getString("admin"));
                userMsg.setRoot(this.rs.getString("root"));
                userMsg.setAdmin_id(this.rs.getInt("admin_id"));
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
        return userMsg;
    }

    @Override
    public UserMsg selectUserMsgByUserNameAndPasswordAndAdminID(String username, String password, Integer admin_id)
    {
        UserMsg userMsg = new UserMsg();
        String sql = "select * from usermsg where admin_id=" + admin_id + " and username='" + username + "' and password='" + password + "'";
        try
        {
            this.con = getConnection();

            this.pstmt = ((PreparedStatement)this.con.prepareStatement(sql));
            this.rs = this.pstmt.executeQuery();
            while (this.rs.next())
            {
                userMsg.setId(this.rs.getInt("id"));
                userMsg.setUsername(this.rs.getString("username"));
                userMsg.setPassword(this.rs.getString("password"));
                userMsg.setAdmin(this.rs.getString("admin"));
                userMsg.setRoot(this.rs.getString("root"));
                userMsg.setAdmin_id(this.rs.getInt("admin_id"));
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
        return userMsg;
    }

    @Override
    public String selectEmailByAdminAndAdminId(Integer admin_id)
    {
        String email = "";
        String sql = "select email from usermsg where admin_id=" + admin_id + " and admin='Y'";
        try
        {
            this.con = getConnection();
            this.pstmt = ((PreparedStatement)this.con.prepareStatement(sql));
            this.rs = this.pstmt.executeQuery();
            if (this.rs.next()) {
                email = this.rs.getString("email");
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
        return email;
    }

    @Override
    public void updatePassByNameAndAdminId(String password, String username, Integer admin_id)
    {
        String sql = "update usermsg set password ='" + password + "' where username='" + username + "' and admin_id=" + admin_id;
        try
        {
            this.con = getConnection();
            this.pstmt = ((PreparedStatement)this.con.prepareStatement(sql));
            this.pstmt.executeUpdate();
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
    public void deleteAllFileByName(String fileName)
    {
        String sql = "delete from upmsg where name='" + fileName + "'";
        try
        {
            this.con = getConnection();
            this.pstmt = ((PreparedStatement)this.con.prepareStatement(sql));
            this.pstmt.executeUpdate();
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
    public void deleteFileByNameAndUserID(String fileName, Integer userId)
    {
        String sql = "delete from upmsg where name='" + fileName + "' and user_id =" + userId;
        try
        {
            this.con = getConnection();
            this.pstmt = ((PreparedStatement)this.con.prepareStatement(sql));
            this.pstmt.executeUpdate();
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
    public void deleteFileByNameAndAdminId(String fileName, Integer adminId)
    {
        String sql = "delete from upmsg where name='" + fileName + "' and admin_id =" + adminId;
        try
        {
            this.con = getConnection();
            this.pstmt = ((PreparedStatement)this.con.prepareStatement(sql));
            this.pstmt.executeUpdate();
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
    public List<UserMsg> selectAll()
    {
        List<UserMsg> list = new ArrayList();
        String sql = "select * from usermsg ";
        try
        {
            this.con = getConnection();
            this.pstmt = ((PreparedStatement)this.con.prepareStatement(sql));
            this.rs = this.pstmt.executeQuery();
            UserMsg userMsg = new UserMsg();
            while (this.rs.next())
            {
                userMsg.setId(this.rs.getInt("id"));
                userMsg.setAdmin_id(this.rs.getInt("admin_id"));
                userMsg.setAdmin(this.rs.getString("admin"));
                userMsg.setRoot(this.rs.getString("root"));
                userMsg.setUsername(this.rs.getString("username"));
                userMsg.setPassword(this.rs.getString("password"));
                list.add(userMsg);
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
    public void updateUserPassToMD5(String password, int id)
    {
        String sql = "update usermsg set password ='" + password + "' where id=" + id;
        try
        {
            this.con = getConnection();
            this.pstmt = ((PreparedStatement)this.con.prepareStatement(sql));
            this.pstmt.executeUpdate();
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
}
