/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.stockmgmt.dao;

import in.stockmgmt.dbutil.DBConnection;
import in.stockmgmt.pojo.OrdersPojo;
import in.stockmgmt.pojo.ProductsPojo;
import in.stockmgmt.pojo.UserProfile;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Lenovo
 */
public class OrderDAO {

    public static String getNextOrderId() throws SQLException {
        Connection conn = DBConnection.getConnection();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("Select max(order_id) from orders");
        rs.next();
        String orderId = rs.getString(1);
        if (orderId == null) {
            return "O-101";
        }
        int orderno = Integer.parseInt(orderId.substring(2));
        return "O-" + (orderno + 1);
    }

    public static boolean addOrder(ArrayList<ProductsPojo> al, String ordId) throws SQLException {
        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement("insert into orders values(?,?,?,?)");
        int count = 0;
        for (ProductsPojo p : al) {
            ps.setString(1, ordId);
            ps.setString(2, p.getProductId());
            ps.setInt(3, p.getQuantity());
            ps.setString(4, UserProfile.getUserid());
            count = count + ps.executeUpdate();
        }
        return count == al.size();
    }

    public static List<String> getAllOrderId() throws SQLException {
        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement("select distinct order_id from orders where userid=? order by order_id");
        ps.setString(1, UserProfile.getUserid());
        List<String> OrderIdList = new ArrayList<>();
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            OrderIdList.add(rs.getString(1));
        }
        return OrderIdList;
    }

    public static List<OrdersPojo> getProductsByOrderId(String O_ID) throws SQLException {
        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement("select * from products FULL OUTER JOIN orders on products.p_id=orders.p_id where order_id=?");
        ps.setString(1, O_ID);
        List<OrdersPojo> productsList=new ArrayList<>();
        ResultSet rs=ps.executeQuery();
        while(rs.next()){
           OrdersPojo prod=new OrdersPojo();
           prod.setProductId(rs.getString(1));
           prod.setProductName(rs.getString(2));
           prod.setProductCompany(rs.getString(3));
           prod.setProductprice(rs.getDouble(4));
           prod.setOurPrice(rs.getDouble(5));
           prod.setTax(rs.getInt(6));
           prod.setQuantity(rs.getInt(11));
           prod.setUserid(rs.getString(12));
           productsList.add(prod);
       }
       return productsList;
    }
    public static List<String> getAllOrderIdForManager() throws SQLException {
        Connection conn = DBConnection.getConnection();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("select distinct order_id from orders order by order_id");
        List<String> OrderIdList = new ArrayList<>();
        while (rs.next()) {
            OrderIdList.add(rs.getString(1));
        }
        return OrderIdList;
    }
}
