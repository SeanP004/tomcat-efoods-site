package model.dao;

import model.exception.*;
import model.catalog.*;
import java.sql.*;
import javax.sql.*;
import javax.naming.*;
import java.util.*;

/**
 * Implementation of the Catalog database access object. Allows the application
 * to query for a list of categories or items, or an individual category or item
 * given its unique identifier.
 *
 * Utilizes the database connection at jdbc/EECS,
 * and utilizes pooled connections.
 */
public class CatalogDBAO implements CatalogDB {

    private DataSource datasource;

    /**
     * CatalogDBAO constructor. Obtains a reference to the pooled connections to
     * the data source.
     *
     * @throws      NamingException
     */
    public CatalogDBAO() throws NamingException {
        Context initCtx = new InitialContext();
        Context envCtx = (Context) initCtx.lookup("java:comp/env");
        datasource = (DataSource) envCtx.lookup("jdbc/EECS");
    }

    // Private

    /**
     * Returns a free connection to the database.
     *
     * @return      a connection to the database
     * @throws      SQLException
     */
    private Connection getConnection() throws SQLException {
        Connection con = datasource.getConnection();
        con.createStatement().executeUpdate("set schema roumani");
        return con;
    }

    /**
     * Closes and releases all database related resources, including releasing
     * the connection back to the pool.
     *
     * @param rs    the result set
     * @param ps    the prepared statement
     * @param conn  the connection
     * @throws      SQLException
     */
    private void close(ResultSet rs, PreparedStatement ps, Connection conn)
            throws SQLException {
        rs.close();
        ps.close();
        conn.close();
    }

    /**
     * Creates a new Item and populates its properties given a result set.
     *
     * @param rs    the given result set from the database
     * @return      the resulting Item
     * @throws      SQLException
     */
    private Item makeItem(ResultSet rs) throws SQLException {
        Item item = new Item();
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
        return item;
    }

    /**
     * Creates a new list of Items and populates the properties of the newly
     * created Items given a result set.
     *
     * @param rs    the given result set from the database
     * @return      the resulting list of Items
     * @throws      SQLException
     */
    private List<Item> makeItemList(ResultSet rs) throws SQLException {
        List<Item> items = new ArrayList<Item>();
        while (rs.next()) {
            items.add(makeItem(rs));
        }
        return items;
    }

    /**
     * Creates a new Category and populates its properties given a result
     * set.
     *
     * @param rs    the given result set from the database
     * @return      the resulting Category
     * @throws      SQLException
     */
    private Category makeCategory(ResultSet rs) throws SQLException {
        Blob pic = rs.getBlob("picture");
        Category category = new Category();
        category.setId(rs.getInt("id"));
        category.setName(rs.getString("name"));
        category.setDescription(rs.getString("description"));
        category.setPicture(pic.getBytes(1, (int) pic.length()));
        return category;
    }

    /**
     * Creates a new list of Categories and populates the properties of the
     * newly created Categories given a result set.
     *
     * @param rs    the given result set from the database
     * @return      the resulting list of Categories
     * @throws      SQLException
     */
    private List<Category> makeCategoryList(ResultSet rs)
            throws SQLException {
        List<Category> categories = new ArrayList<Category>();
        while (rs.next()) {
            categories.add(makeCategory(rs));
        }
        return categories;
    }

    // Public

    /**
     * Returns a list of all the categories that exist in the database and
     * satisfy the given filtering rules.
     *
     * @param filter    specifies filtering rules
     * @return          list of categories
     * @throws          DataAccessException
     */
    public List<Category> getCategories(CategoryFilter filter)
            throws DataAccessException {
        try {
            Connection conn = getConnection();
            PreparedStatement ps = CatalogDBQuery.getCategories(filter, conn);
            ResultSet rs = ps.executeQuery();
            List<Category> categories = makeCategoryList(rs);
            close(rs, ps, conn);
            return categories;
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    /**
     * Returns a list of all the items that exist in the database and satisfy
     * the given filtering rules.
     *
     * @param filter    specifies filtering rules
     * @return          list of items
     * @throws          DataAccessException
     */
    public List<Item> getItems(ItemFilter filter) throws DataAccessException {
        try {
            Connection conn = getConnection();
            PreparedStatement ps = CatalogDBQuery.getItems(filter, conn);
            ResultSet rs = ps.executeQuery();
            List<Item> items = makeItemList(rs);
            close(rs, ps, conn);
            return items;
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    /**
     * Returns the category that corresponds to the given category unique
     * identifer.
     *
     * @param id    the category unique identifer
     * @return      the corresponding category or null
     * @throws      DataAccessException , CategoryNotFoundException
     */
    public Category getCategory(int id) throws DataAccessException,
            CategoryNotFoundException {
        try {
            Connection conn = getConnection();
            PreparedStatement ps = CatalogDBQuery.getCategory(id, conn);
            ResultSet rs = ps.executeQuery();
            Category category = rs.next() ? makeCategory(rs) : null;
            close(rs, ps, conn);
            if (category == null) { throw new CategoryNotFoundException(); }
            return category;
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    /**
     * Returns the item that corresponds to the given item unique identifer.
     *
     * @param number    the item unique identifer
     * @return          the corresponding item or null
     * @throws          DataAccessException , ItemNotFoundException
     */
    public Item getItem(String number) throws DataAccessException,
            ItemNotFoundException {
        try {
            Connection conn = getConnection();
            PreparedStatement ps = CatalogDBQuery.getItem(number, conn);
            ResultSet rs = ps.executeQuery();
            Item item = rs.next() ? makeItem(rs) : null;
            close(rs, ps, conn);
            if (item == null) { throw new ItemNotFoundException(); }
            return item;
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    /**
     * Returns the maximum price of all items
     * in the Item relation in the Catalog database.
     *
     * @return      the maximum price of the items
     * @throws      DataAccessException
     */
    public double getItemMaxPrice() throws DataAccessException {
        try {
            Connection conn = getConnection();
            PreparedStatement ps = CatalogDBQuery.getItemMaxPrice(conn);
            ResultSet rs = ps.executeQuery();
            double maxPrice = rs.next() ? rs.getDouble("price") : null;
            close(rs, ps, conn);
            return maxPrice;
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }
    
} // CatalogDBAO
