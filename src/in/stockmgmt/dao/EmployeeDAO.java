/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.stockmgmt.dao;

import in.stockmgmt.dbutil.DBConnection;
import in.stockmgmt.pojo.EmployeePojo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO {
    public static String getNextEmpId() throws SQLException{
        Connection conn=DBConnection.getConnection();
        Statement st=conn.createStatement();
        ResultSet rs=st.executeQuery("Select max(empid) from employees");
        rs.next();
        String empid=rs.getString(1);
        int empno=Integer.parseInt(empid.substring(1));
        return "E"+(empno+1);
    }
    public static boolean addEmployee(EmployeePojo emp) throws SQLException{
        Connection conn=DBConnection.getConnection();
        PreparedStatement ps=conn.prepareStatement("Insert into employees values(?,?,?,?)");
        ps.setString(1,emp.getEmpid());
        ps.setString(2, emp.getEmpname());
        ps.setString(3,emp.getJob());
        ps.setDouble(4, emp.getSalary());
        int result=ps.executeUpdate();
        return result==1;
    }
    public static List<EmployeePojo> getAllEmployees() throws SQLException{
       Connection conn=DBConnection.getConnection();
       Statement st=conn.createStatement();
       ResultSet rs=st.executeQuery("Select * from employees order by empid");
       ArrayList <EmployeePojo> empList=new ArrayList<>();
       while(rs.next()){
           EmployeePojo emp=new EmployeePojo();
           emp.setEmpid(rs.getString(1));
           emp.setEmpname(rs.getString(2));
           emp.setJob(rs.getString(3));
           emp.setSalary(rs.getDouble(4));
           empList.add(emp);
       }
       return empList;
    }
    public static boolean updateEmployee(EmployeePojo emp) throws SQLException{
        Connection conn=DBConnection.getConnection();
        PreparedStatement ps=conn.prepareStatement("update employees set empname=?,job=?,salary=? where empid=?");
        ps.setString(1, emp.getEmpname());
        ps.setString(2,emp.getJob());
        ps.setDouble(3, emp.getSalary());
        ps.setString(4,emp.getEmpid());
        int result=ps.executeUpdate();
        if(result==0)
            return false;
        else{
            boolean x=UserDAO.isUserPresent(emp.getEmpid());
            if(x==false)
                return true;
        ps=conn.prepareStatement("Update users set username=?,usertype=? where empid=?");
        ps.setString(1, emp.getEmpname());
        ps.setString(2,emp.getJob());
        ps.setString(3,emp.getEmpid());
        int result2=ps.executeUpdate();
        return result2==1;
        }
    }
    public static ArrayList<String> getAllEmpId()throws  SQLException{
        Connection conn=DBConnection.getConnection();
        Statement stm=conn.createStatement();
        ResultSet rs=stm.executeQuery("select empid from employees");
        ArrayList<String> empid=new ArrayList<>();
        while(rs.next()){
            empid.add(rs.getString(1));
        }
        return empid;
    }
    public static EmployeePojo getEmployeeById(String EmpId)throws SQLException{
        Connection conn=DBConnection.getConnection();
        PreparedStatement ps=conn.prepareStatement("select * from employees where empid=?");
        ps.setString(1, EmpId);
        EmployeePojo emp=new EmployeePojo();
        ResultSet rs=ps.executeQuery();
        rs.next();
        emp.setEmpname(rs.getString(2));
        emp.setJob(rs.getString(3));
        emp.setSalary(rs.getDouble(4));
        return emp;
    }
    public static boolean deleteEmployee(String empid)throws SQLException
    {
        Connection conn=DBConnection.getConnection();
        PreparedStatement ps=conn.prepareStatement("delete from employees where empid=?");
        ps.setString(1, empid);
        int x=ps.executeUpdate();
        return x==1;
    }
    
}
