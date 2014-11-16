package model.catalog;

import java.util.*;
import javax.naming.*;
import model.database.*;
import model.exception.*;

public class Catalog {

    private final CatalogDB dao;

    /**
     * Catalog constructor prepare DAO to retrive data from database.
     *
     * @throws      DataAccessException
     */
    public Catalog() throws DataAccessException {
        try {
            dao = new CatalogDBAO();
        } catch (NamingException e) {
            throw new DataAccessException(
                    "Could not create data access handler.", e);
        }
    }

    // Private

    /**
     * Parse a String as an Integer. Returns integer value if parseable
     * otherwise throws InvalidQueryFilterException.
     *
     * @param value     to be parsed
     * @return          integer value
     * @throws          InvalidQueryFilterException
     */
    private int parseInt(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new InvalidQueryFilterException("Invalid integer format.", e);
        }
    }

    /**
     * Parse a String as a Double precision Float point. Returns double value if
     * parseable otherwise throws InvalidQueryFilterException.
     *
     * @param value     to be parsed
     * @return          double value
     * @throws          InvalidQueryFilterException
     */
    private double parseDouble(String value) {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            throw new InvalidQueryFilterException("Invalid number format.", e);
        }
    }

    /**
     * Create an Category Filter according to the value of the retrived
     * variable and send to the correct corresponding value set
     *
     * @param orderBy       the sort order
     * @param searchTerm    the search query string
     * @param offset        pagination offset
     * @param fetch         number of items to return
     * @return              CategoryFilter
     * @throws              InvalidQueryFilterException
     */
    private CategoryFilter parseCategoryFilter(String orderBy,
            String searchTerm, String offset, String fetch) {
        CategoryFilter filter = new CategoryFilter();

        if (orderBy != null) {
            filter.setOrderBy(orderBy); }
        if (searchTerm != null) {
            filter.setSearchTerm(searchTerm); }
        if (offset != null) {
            filter.setOffset(parseInt(offset)); }
        if (fetch != null) {
            filter.setFetch(parseInt(fetch)); }

        return filter;
    }

    /**
     * Create an Item Filter according to the value of the retrived variable and
     * send to the correct corresponding value set
     *
     * @param orderBy       the sort order
     * @param searchTerm    the search query string
     * @param category      the category filter
     * @param offset        pagination offset
     * @param fetch         number of items to return
     * @param minPrice      minimum price of items
     * @param maxPrice      maximum price of items
     * @return              ItemFilter
     * @throws              InvalidQueryFilterException
     */
    private ItemFilter parseItemFilter(String orderBy, String searchTerm,
            String category, String offset, String fetch, String minPrice,
            String maxPrice) {
        ItemFilter filter = new ItemFilter();

        if (orderBy != null) {
            filter.setOrderBy(orderBy); }
        if (searchTerm != null) {
            filter.setSearchTerm(searchTerm); }
        if (category != null) {
            filter.setCategory(parseInt(category)); }
        if (offset != null) {
            filter.setOffset(parseInt(offset)); }
        if (fetch != null) {
            filter.setFetch(parseInt(fetch)); }
        if (minPrice != null) {
            filter.setMinPrice(parseDouble(minPrice)); }
        if (maxPrice != null) {
            filter.setMaxPrice(parseDouble(maxPrice)); }

        return filter;
    }

    // Public

    /**
     * Returns a list of categories given the specified filtering rules.
     * The returned list is filtered by search term or by pagination and sorted
     * as specified.
     *
     * @param orderBy       the sort order
     * @param searchTerm    the search query string
     * @param offset        pagination offset
     * @param fetch         number of items to return
     * @return              list of categories
     * @throws              InvalidQueryFilterException
     */
    public List<Category> getCategories(String orderBy,
            String searchTerm, String offset, String fetch)
            throws InvalidQueryFilterException {
        return dao.getCategories(parseCategoryFilter(orderBy, searchTerm,
                offset, fetch));
    }

    /**
     * Returns a list of categories given the specified filtering rules.
     * The returned list is filtered by search term or by pagination and sorted
     * as specified.
     *
     * @param orderBy       the sort order
     * @param searchTerm    the search query string
     * @param offset        pagination offset
     * @param fetch         number of items to return
     * @return              list of categories
     * @throws              InvalidQueryFilterException
     */
    public CategoryList getCategoryList(String orderBy,
            String searchTerm, String offset, String fetch)
            throws InvalidQueryFilterException {
        CategoryFilter filter = parseCategoryFilter(orderBy, searchTerm,
                offset, fetch);
        return new CategoryList(dao.getCategories(filter), filter);
    }


    /**
     * Returns the iten category given the specified category id.
     *
     * @param id    the category unique identifier
     * @return      the category corresponding to the given id.
     */
    public Category getCategory(String id) {
        return dao.getCategory(parseInt(id));
    }

    /**
     * Returns a list of items given the specified filtering rules. The returned
     * list is filtered by search term, price range, category or by pagination
     * and sorted as specified.
     *
     * @param orderBy       the sort order
     * @param searchTerm    the search query string
     * @param category      the category filter
     * @param offset        pagination offset
     * @param fetch         number of items to return
     * @param minPrice      minimum price of items
     * @param maxPrice      maximum price of items
     * @return              list of items
     * @throws              InvalidQueryFilterException
     */
    public List<Item> getItems(String orderBy, String searchTerm,
            String category, String offset, String fetch, String minPrice,
            String maxPrice) throws InvalidQueryFilterException {
        return dao.getItems(parseItemFilter(orderBy, searchTerm, category,
                offset, fetch, minPrice, maxPrice));
    }

    /**
     * Returns a list of items given the specified filtering rules. The returned
     * list is filtered by search term, price range, category or by pagination
     * and sorted as specified.
     *
     * @param orderBy       the sort order
     * @param searchTerm    the search query string
     * @param category      the category filter
     * @param offset        pagination offset
     * @param fetch         number of items to return
     * @param minPrice      minimum price of items
     * @param maxPrice      maximum price of items
     * @return              list of items
     * @throws              InvalidQueryFilterException
     */
    public ItemList getItemList(String orderBy, String searchTerm,
            String category, String offset, String fetch, String minPrice,
            String maxPrice) throws InvalidQueryFilterException {
        ItemFilter filter = parseItemFilter(orderBy, searchTerm, category,
                offset, fetch, minPrice, maxPrice);
        return new ItemList(dao.getItems(filter), filter);
    }

    /**
     * Returns the iten given the specified item number.
     *
     * @param number    the iten unique identifier
     * @return          the item corresponding to the given number.
     */
    public Item getItem(String number) {
        return dao.getItem(number);
    }

} // Catalog
