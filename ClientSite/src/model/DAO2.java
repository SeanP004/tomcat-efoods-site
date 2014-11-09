package model;

import java.sql.*;
import java.util.*;
import javax.naming.*;
import javax.sql.*;

public class DAO2 {

    private final DataSource ds;

    private final String SQL_GET_CATEGORY = "SELECT * FROM CATEGORY";
    private final String SQL_GET_ITEM = "SELECT * FROM ITEM";
    //private final String SQL_ORDERED  = "ORDER BY S.$field";

    public DAO2() throws NamingException {
        ds = (DataSource)(new InitialContext()).lookup("java:/comp/env/jdbc/EECS");
    }

    public List<CategoryBean> retrieveCategory() throws SQLException {
        String sqlStmt = SQL_GET_CATEGORY;

        Connection con = ds.getConnection();
            con.createStatement().executeUpdate("set schema roumani");
            PreparedStatement ps = con.prepareStatement(sqlStmt);
            List<CategoryBean> categoryList = new ArrayList<CategoryBean>();
            ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    CategoryBean item = new CategoryBean();
                        item.setID(rs.getInt("ID"));
                        item.setName(rs.getString("name"));
                        item.setDescription(rs.getString("description"));
                        //item.setPicture(rs.getString("picture"));
                    categoryList.add(item);
                }
            rs.close();
        con.close();
        return categoryList;
    }

    
    public List<ItemBean> retrieveItem() throws SQLException {
        String sqlStmt = SQL_GET_ITEM;

        Connection con = ds.getConnection();
            con.createStatement().executeUpdate("set schema roumani");
            PreparedStatement ps = con.prepareStatement(sqlStmt);
            List<ItemBean> itemList = new ArrayList<ItemBean>();
            ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    ItemBean item = new ItemBean();
                        item.setNumber(rs.getString("number"));
                        item.setName(rs.getString("name"));
                        item.setPrice(rs.getDouble("price"));
                        item.setQty(rs.getInt("qty"));
                        item.setOnOrder(rs.getInt("onorder"));
                        item.setReOrder(rs.getInt("reorder"));
                        item.setCatId(rs.getInt("catid"));
                        item.setSupId(rs.getInt("supid"));
                        item.setCostPrice(rs.getDouble("costprice"));
                        item.setUnit(rs.getString("unit"));
                    itemList.add(item);
                }
            rs.close();
        con.close();
        return itemList;
    }
}
