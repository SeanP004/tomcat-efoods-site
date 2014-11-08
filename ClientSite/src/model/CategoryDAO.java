package model;

import java.sql.*;
import java.util.*;
import javax.naming.*;
import javax.sql.*;

public class CategoryDAO {

    private final DataSource ds;
    private final Map<String, String> sorts;
    private final String SQL_RETRIEVE = "SELECT * FROM CATEGORY";
    private final String SQL_ORDERED  = "ORDER BY S.$field";

    public CategoryDAO(Map<String, String> sortMap) throws NamingException {
        ds = (DataSource)(new InitialContext()).lookup("java:/comp/env/jdbc/EECS");
        sorts = sortMap;
    }

    public List<CategoryBean> retrieve() throws SQLException {
        return retrieve(null);
    }

    public List<CategoryBean> retrieve(String orderBy) throws SQLException {
        String sqlStmt = SQL_RETRIEVE;
        if (orderBy != null && sorts.containsKey(orderBy)) {
            sqlStmt += " " + SQL_ORDERED.replace("$field", orderBy);
        }
        Connection con = ds.getConnection();
            con.createStatement().executeUpdate("set schema roumani");
            PreparedStatement ps = con.prepareStatement(sqlStmt);
            List<CategoryBean> category = new ArrayList<CategoryBean>();
            ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    CategoryBean item = new CategoryBean();
                    	item.setID(rs.getInt("ID"));
                    	item.setName(rs.getString("name"));
                    	item.setDescription(rs.getString("description"));
                    	item.setPicture(rs.getString("picture"));
                    category.add(item);
                }
            rs.close();
        con.close();
        return category;
    }

}
