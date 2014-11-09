package model;

import java.sql.*;
import java.util.*;
import javax.naming.*;

public class FoodSys {

    private final DAO dao;
    private final String errDBCP = "Could not init database connection pools.";
    private final String errSQL  = "Could not execute database query.";

    public FoodSys() {
        try {
            dao = new DAOImpl();
        } catch (NamingException e) {
            throw new RuntimeException(errDBCP);
        }
    }

    public List<CategoryBean> getCategories() throws Exception {
        try {
            return dao.getCategories(new GetCategoryOption());
        } catch (SQLException e) {
            Exception ex = new RuntimeException(errSQL);
            ex.setStackTrace(e.getStackTrace());
            throw ex;
        } 
    }

    public List<ItemBean> getItems() throws Exception {
        try {
            return dao.getItems(new GetItemOption());
        } catch (SQLException e) {
            Exception ex = new RuntimeException(errSQL);
            ex.setStackTrace(e.getStackTrace());
            throw ex;
        } 
    }

} // SIS
