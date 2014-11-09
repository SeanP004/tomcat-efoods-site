package model;

import java.sql.*;
import java.util.*;
import javax.naming.*;

public class FoodSystem {

    private final DAO dao;
    private final String errDBCP = "Could not init database connection pools.";
    private final String errSQL  = "Could not execute database query.";

    public FoodSystem() {
        try {
            dao = new DAO();
        } catch (NamingException e) {
            throw new RuntimeException(errDBCP);
        }
    }

    public List<CategoryBean> retrieveCategory() throws Exception {
        try {
            return dao.retrieveCategory();
        } catch (SQLException e) {
            Exception ex = new RuntimeException(errSQL);
            ex.setStackTrace(e.getStackTrace());
            throw ex;
        } 
    }
    
    public List<ItemBean> retrieveItem() throws Exception {
        try {
            return dao.retrieveItem();
        } catch (SQLException e) {
            Exception ex = new RuntimeException(errSQL);
            ex.setStackTrace(e.getStackTrace());
            throw ex;
        } 
    }

} // SIS
