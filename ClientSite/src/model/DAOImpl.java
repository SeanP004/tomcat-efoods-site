package model;

import java.sql.*;
import java.util.*;

import javax.naming.*;
import javax.sql.*;

public class DAOImpl implements DAO {

    private final DataSource ds;

    private final String SQL_GET_CATEGORY = "SELECT * FROM CATEGORY";
    private final String SQL_GET_ITEM = "SELECT * FROM ITEM I"
    				  		+ " WHERE (I.name LIKE ? OR I.number LIKE ? OR ? IS NULL)"
    				  		+ " AND (I.price >= ? OR ? = -1)"
    				  		+ " AND (I.price <= ? OR ? = -1)"
    				  		+ " AND (I.catid IN (SELECT ID FROM CATEGORY C WHERE C.name LIKE ?) OR ? IS NULL)";
    private final String SQL_ORDERED  = "ORDER BY I.$field";

    public DAOImpl() throws NamingException {
        ds = (DataSource)(new InitialContext()).lookup("java:/comp/env/jdbc/EECS");
    }

    @Override
    public List<CategoryBean> getCategories(GetCategoryOption opts) throws SQLException {
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

    @Override
    public List<ItemBean> getItems(GetItemOption opts) throws SQLException {
        String sqlStmt = SQL_GET_ITEM;
        Connection con = ds.getConnection();
        

        if (opts.orderBy != null) {
            sqlStmt += " " + SQL_ORDERED.replace("$field", opts.orderBy);
        }

        //System.out.println(opts.searchTerm);
        //System.out.println(opts.category);
        //System.out.println(opts.minPrice);
        //System.out.println(opts.maxPrice);
        
            con.createStatement().executeUpdate("set schema roumani");
            PreparedStatement ps = con.prepareStatement(sqlStmt);
            	//searchTerm
            	ps.setString(1, "%" + opts.searchTerm + "%");
            	ps.setString(2, "%" + opts.searchTerm + "%");
            	ps.setString(3, opts.searchTerm);
            	//minPrice
            	ps.setDouble(4, opts.minPrice);
            	ps.setDouble(5, opts.minPrice);
            	//maxPrice
            	ps.setDouble(6, opts.maxPrice);
            	ps.setDouble(7, opts.maxPrice);
            	//categroy
            	ps.setString(8, "%" + opts.category + "%");
            	ps.setString(9, opts.category);
            	
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

    @Override
    public ItemBean getItemById(String id) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }
}
