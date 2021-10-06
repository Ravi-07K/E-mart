/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.stockmgmt.dao;

import in.stockmgmt.dbutil.DBConnection;
import in.stockmgmt.pojo.ReceptionistPojo;
import in.stockmgmt.pojo.UserPojo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Lenovo
 */
public class ReceptionistDAO {
    public static Map<String,String> getNonRegisteredReceptionist()throws  SQLException{
        Connection conn=DBConnection.getConnection();
        Statement stm=conn.createStatement();
        ResultSet rs=stm.executeQuery("select empid,empname from employees where job='Receptionist' and empid not in (select empid from users where usertype='Receptionist')");
        HashMap<String,String> receptionistList=new HashMap<>();
        while(rs.next()){
            String id=rs.getString(1);
            String name=rs.getString(2);
            receptionistList.put(id, name);
        }
        return receptionistList;
    }
        public static boolean addReceptionist(UserPojo user) throws SQLException{
        Connection conn=DBConnection.getConnection();
        PreparedStatement ps=conn.prepareStatement("Insert into users values(?,?,?,?,?)");
        ps.setString(1,user.getUserid());
        ps.setString(2, user.getEmpid());
        ps.setString(3,user.getPassword());
        ps.setString(4, user.getUsertype());
        ps.setString(5, user.getUsername());

        int result=ps.executeUpdate();
        return result==1;
    }


    public static UserPojo getUserById(String userid) throws SQLException {
        Connection conn=DBConnection.getConnection();
        PreparedStatement ps=conn.prepareStatement("select * from users where userid=?");
        ps.setString(1, userid);
        UserPojo user=new UserPojo();
        ResultSet rs=ps.executeQuery();
        while(rs.next()){
        user.setUserid(rs.getString(1));
        user.setEmpid(rs.getString(2));
        user.setPassword(rs.getString(3));
        user.setUsertype(rs.getString(4));
        user.setUsername(rs.getString(5));
        }
        return user;
    }

    public static boolean updateReceptionist(String userid,String pwd) throws SQLException {
        Connection conn=DBConnection.getConnection();
        PreparedStatement ps=conn.prepareStatement("update users set password=? where userid=?");
        ps.setString(1,pwd);
        ps.setString(2, userid);
        return ps.executeUpdate()==1;
    }

    public static boolean deleteReceptionist(String rcpid) throws SQLException {
        Connection conn=DBConnection.getConnection();
        PreparedStatement ps=conn.prepareStatement("delete from users where userid=?");
        ps.setString(1, rcpid);
        int x=ps.executeUpdate();
        return x==1;
    }

    public static List<ReceptionistPojo> getAllReceptionist()throws SQLException {
        Connection conn=DBConnection.getConnection();
       Statement st=conn.createStatement();
       ResultSet rs=st.executeQuery("Select users.empid,empname,userid,job,salary from users,employees where usertype='Receptionist' and users.empid=employees.empid order by empid");
       ArrayList <ReceptionistPojo> rcpList=new ArrayList<>();
       while(rs.next()){
           ReceptionistPojo recep=new ReceptionistPojo();
           recep.setEmpid(rs.getString(1));
           recep.setEmpname(rs.getString(2));
           recep.setUserid(rs.getString(3));
           recep.setJob(rs.getString(4));
           recep.setSalary(rs.getDouble(5));
           rcpList.add(recep);
       }
       return rcpList;
    }
    public static Map<String,String> getAllReceptionistId()throws SQLException{
        Connection conn=DBConnection.getConnection();
        Statement st=conn.createStatement();
        ResultSet rs=st.executeQuery("select userid,username from users where usertype='Receptionist' order by empid");
        HashMap<String,String> receptionistList=new HashMap<>();
        while(rs.next()){
            String id=rs.getString(1);
            String name=rs.getString(2);
            receptionistList.put(id, name);
        }
        return receptionistList;
    }
    public static List<String> getAllReceptionistUserId() throws SQLException{
        Connection conn=DBConnection.getConnection();
        Statement st=conn.createStatement();
        ResultSet rs=st.executeQuery("select userid from users where usertype='Receptionist' order by empid");
        List<String> receptionistList=new ArrayList<>();
        while(rs.next()){
            String id=rs.getString(1);
            receptionistList.add(id);
        }
        return receptionistList;
    }
}
