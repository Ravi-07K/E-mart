/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.stockmgmt.dbutil;

import java.sql.*;
import javax.swing.JOptionPane;


public class DBConnection {
    private static Connection conn;
    static{
        try{
            Class.forName("oracle.jdbc.OracleDriver");
            conn=DriverManager.getConnection("jdbc:oracle:thin:@//LAPTOP-MTSJP1BT:1521/XE", "grocery", "grocery");
            JOptionPane.showMessageDialog(null, "Connection opened successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
        catch(ClassNotFoundException ex){
            JOptionPane.showMessageDialog(null, "Error in loading the driver","Driver Error!",JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
            System.exit(1);
        }
        catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "Error in opening the Connection", "Driver Error!", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
            System.exit(1);
        }
        
    }
    public static Connection getConnection(){
            return conn;
        }
    public static void closeConnection(){
        try{
            conn.close();
            JOptionPane.showMessageDialog(null, "Connection closed successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
        catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "Error in closing connection", "DB Error!", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
        
    }
}
