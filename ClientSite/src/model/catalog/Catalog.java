package model.catalog;

import java.io.*;
import java.sql.*;
import java.util.*;

import javax.naming.*;

import model.database.*;
import model.exception.*;

public class Catalog {

    private final CatalogDB dao;

    /**
     * Catalog constructor prepare DAO
     * to retrive data from database.
     *
     * @throws DAOCreationException 
     */
    public Catalog() throws DAOCreationException {
        try {
            dao = new CatalogDBAO();
        } catch (NamingException e) {
            throw new DAOCreationException(e);
        }
    }

    /**
     * Parse a String as an Integer.
     * Returns integer value if parseable
     * otherwise return -1.
     *
     * @param  value to be parsed
     * @return       integer value or -1
     */
    private int parseInt(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    /**
     * Parse a String as a Double precision
     * Float point. Returns double value if
     * parseable otherwise return -1.
     *
     * @param  value to be parsed
     * @return       double value or -1
     */
    private double parseDouble(String value) {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private ItemCategoryFilter parseItemCategoryFilter(String orderBy,
    			String searchTerm, String offset, String fetch) throws Exception {
    	ItemCategoryFilter filter = new ItemCategoryFilter();
    	filter.setOrderBy(orderBy);
    	filter.setSearchTerm(searchTerm);
    	filter.setOffset(Integer.parseInt(offset));
    	filter.setFetch(Integer.parseInt(fetch));
    	return filter;
    }

    /**
     * Create an ItemCategory Filter according to the value of the retrived
     * variable and send to the correct corresponding value set
     *
     * @param orderBy       string type
     * @param searchTerm    string type
     * @param category      string type
     * @param offset        string type
     * @param fetch         string type
     * @param minPrice      string type
     * @param maxPrice      string type
     *
     * @return ItemFilter
     */
    private ItemFilter parseItemFilter(String orderBy, String searchTerm,
        		String category, String offset, String fetch,
                String minPrice, String maxPrice) throws Exception {
        ItemFilter filter = new ItemFilter();

        if (orderBy != null) {
            filter.setOrderBy(orderBy);}
        if (searchTerm != null) {
            filter.setSearchTerm(searchTerm);}
        if (category != null) {
            filter.setCategory(parseInt(category));}
        if (offset != null) {
            filter.setOffset(parseInt(offset));}
        if (fetch != null) {
            filter.setFetch(parseInt(fetch));}
        if (minPrice != null) {
            filter.setMinPrice(parseDouble(minPrice));}
        if (maxPrice != null) {
            filter.setMaxPrice(parseDouble(maxPrice));}

        return filter;
    }

    public List<ItemCategory> getItemCategories(String orderBy,
                String searchTerm, String offset, String fetch) throws Exception {
        return dao.getCategories(parseItemCategoryFilter(orderBy,
                searchTerm, offset, fetch));
    }

    public ItemCategory getItemCategory(String number) throws Exception {
        return dao.getCategory(Integer.parseInt(number));
    }

    public List<Item> getItems(String orderBy, String searchTerm,
    		String category, String offset, String fetch, String minPrice,
    		String maxPrice) throws Exception {
        return dao.getItems(parseItemFilter(orderBy, searchTerm,
                category, offset, fetch, minPrice, maxPrice));
    }

    public Item getItem(String number) throws Exception {
        return dao.getItem(number);
    }

}
