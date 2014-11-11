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
    
    public List<ItemBean> getItems(String queryString) throws Exception {
        try {
        	GetItemOption queryOption = new GetItemOption();
        	Map<String, String> qPairs = parseQuery(queryString);
        	
        	if (qPairs.containsKey("orderBy")) {
        		queryOption.orderBy = qPairs.get("orderBy");
        	}
        	else if (qPairs.containsKey("offset")) {
        		queryOption.offset = Integer.parseInt(qPairs.get("offset"));
        	}
        	else if (qPairs.containsKey("fetch")) {
        		queryOption.fetch = Integer.parseInt(qPairs.get("fetch"));
        	}
        	else if (qPairs.containsKey("category")) {
        		queryOption.category = qPairs.get("category");
        	}
        	else if (qPairs.containsKey("searchTerm")) {
        		queryOption.searchTerm = qPairs.get("searchTerm");
        	}
        	else if (qPairs.containsKey("minPrice")) {
        		queryOption.minPrice = Double.parseDouble(qPairs.get("searchTerm"));
        	}
        	else if (qPairs.containsKey("maxPrice")) {
        		queryOption.maxPrice = Double.parseDouble(qPairs.get("searchTerm"));
        	}
        	
            return dao.getItems(queryOption);
        } catch (SQLException e) {
            Exception ex = new RuntimeException(errSQL);
            ex.setStackTrace(e.getStackTrace());
            throw ex;
        } 
    }
    
    private Map<String, String> parseQuery(String query){
        Map<String, String> query_pairs = new LinkedHashMap<String, String>();
        String[] pairs = query.split("&");
        for (String pair : pairs) {
            int idx = pair.indexOf("=");
            query_pairs.put(pair.substring(0, idx), pair.substring(idx + 1));
        }
        return query_pairs;
    }

} // SIS
