package model.catalog;

import java.io.*;
import java.sql.*;
import java.util.*;

import javax.naming.*;
import javax.xml.*;
import javax.xml.bind.*;
import javax.xml.transform.stream.*;
import javax.xml.validation.*;

import model.database.*;

public class Catalog {
    private final CatalogDBAO dao;
    private final String errDBCP = "Could not init database connection pools.";
    private final String errSQL  = "Could not execute database query.";
    private final String errNUM  = "Malformed should have been Integer value";
    
    /**
     * Catalog constructor prepare db to retrive items according data
     */
    public Catalog() {
        try {
            dao = new CatalogDBAO();
        } catch (NamingException e) {
            throw new RuntimeException(errDBCP);
        }
    }
    
    
    private ItemCategoryFilter parseItemCategoryFilter(String orderBy, 
    			String searchTerm, String offset, String fetch){
    	ItemCategoryFilter filter = new ItemCategoryFilter();
    	filter.setOrderBy(orderBy);
    	filter.setSearchTerm(searchTerm);
    	filter.setOffset(Integer.parseInt(offset));
    	filter.setFetch(Integer.parseInt(fetch));
    	return filter;
    }
    
    private ItemFilter parseItemFilter(String orderBy, String searchTerm, 
    		String category, String offset, String fetch, String minPrice, 
    		String maxPrice){
        ItemFilter filter = new ItemFilter();
        filter.setOrderBy(orderBy);
        filter.setSearchTerm(searchTerm);
        filter.setCategory(Integer.parseInt(category));
        filter.setOffset(Integer.parseInt(offset));
        filter.setFetch(Integer.parseInt(fetch));
        filter.setMinPrice(Double.parseDouble(minPrice));
        filter.setMaxPrice(Double.parseDouble(maxPrice));
        return filter;
    }
    
    public List<ItemCategory> getItemCategories() throws Exception {
        try {
        	System.out.print("getItemCategories() called");
        	return dao.getCategories(new ItemCategoryFilter());
        } catch (Exception e) {
        	Exception ex = new RuntimeException(errSQL);
        	ex.setStackTrace(e.getStackTrace());
        	throw ex;
        }
    }
    
    public List<ItemCategory> getItemCategories(String orderBy, 
    		String searchTerm, String offset, String fetch) throws Exception {
        try {
        	ItemCategoryFilter filter = parseItemCategoryFilter(orderBy, 
        			searchTerm, offset, fetch);
        	return dao.getCategories(filter);
        } catch (Exception e) {
        	Exception ex = new RuntimeException(errSQL);
        	ex.setStackTrace(e.getStackTrace());
        	throw ex;
    	}  
    }
    
    public ItemCategory getItemCategory(String number) throws Exception {
        try {
        	return dao.getCategory(Integer.parseInt(number));
        } catch (Exception e) {
        	Exception ex = new RuntimeException(errSQL);
        	ex.setStackTrace(e.getStackTrace());
        	throw ex;
    	}  
    }
    
    public List<Item> getItems() throws Exception {
        try {
        	System.out.println("Catalog get items");
        	return dao.getItems(new ItemFilter());
        } catch (Exception e) {
        	Exception ex = new RuntimeException(errSQL);
        	ex.setStackTrace(e.getStackTrace());
        	throw ex;
        }
    }

    public List<Item> getItems(String orderBy, String searchTerm, 
    		String category, String offset, String fetch, String minPrice, 
    		String maxPrice) throws Exception {
        try {
            ItemFilter filter = parseItemFilter(orderBy, searchTerm, 
            		category, offset, fetch, minPrice, maxPrice);
            return dao.getItems(filter);
        } catch (Exception e) {
            Exception ex = new RuntimeException(errSQL);
            ex.setStackTrace(e.getStackTrace());
            throw ex;
        }  
    }
    
    public Item getItem(String number) throws Exception {
        try {
        	return dao.getItem(number);
        } catch (Exception e) {
        	Exception ex = new RuntimeException(errSQL);
        	ex.setStackTrace(e.getStackTrace());
        	throw ex;
    	}  
    }
}
