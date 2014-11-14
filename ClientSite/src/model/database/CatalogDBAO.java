package model.database;

import model.exception.*;
import model.catalog.*;
import java.sql.*;
import javax.sql.*;
import javax.naming.*;
import java.util.*;

/**
 * Implementation of the Catalog database access object.
 * Allows the application to query for a list
 * of categories or items, or an individual
 * category or item given its unique identifier.
 *
 * Utilizes the database connection at jdbc/EECS,
 * and utilizes pooled connections.
 */
public class CatalogDBAO implements CatalogDB {

    private DataSource datasource;

    /**
     * CatalogDBAO constructor.
     * Obtains a reference to the pooled connections
     * to the data source.
     *
     * @throws NamingException
     */
    public CatalogDBAO() throws NamingException {
        Context initCtx = new InitialContext();
        Context envCtx = (Context)initCtx.lookup("java:comp/env");
        datasource = (DataSource)envCtx.lookup("jdbc/EECS");
    }

    // Private

    /**
     * Returns a free connection to the database.
     *
     * @return a connection to the database
     * @throws SQLException
     */
    private Connection getConnection() throws SQLException {
        Connection con = datasource.getConnection();
        con.createStatement().executeUpdate("set schema roumani");
        return con;
    }  

    /**
     * Closes and releases all database related resources, 
     * including releasing the connection back to the pool.
     * 
     * @param rs	the result set
     * @param ps	the prepared statement
     * @param conn	the connection
     * @throws 		SQLException
     */
    private void close(ResultSet rs, PreparedStatement ps, 
    			Connection conn) throws SQLException {
    	rs.close();
    	ps.close();
    	conn.close();
    }

    /**
     * Creates a new Item and populates
     * its properties given a result set.
     *
     * @param  rs   the given result set from the database
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
     * Creates a new list of Items and populates
     * the properties of the newly created Items
     * given a result set.
     *
     * @param  rs   the given result set from the database
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
     * Creates a new Item Category and populates
     * its properties given a result set.
     *
     * @param  rs   the given result set from the database
     * @return      the resulting Item Category
     * @throws      SQLException
     */
    private ItemCategory makeItemCategory(ResultSet rs) throws SQLException {
        Blob pic = rs.getBlob("picture");
        ItemCategory category = new ItemCategory();
        category.setId(rs.getInt("id"));
        category.setName(rs.getString("name"));
        category.setDescription(rs.getString("description"));
        category.setPicture(pic.getBytes(1, (int)pic.length()));
        return category;
    }

    /**
     * Creates a new list of Item Categories and populates
     * the properties of the newly created Item Categories
     * given a result set.
     *
     * @param  rs   the given result set from the database
     * @return      the resulting list of Item Categories
     * @throws      SQLException
     */
    private List<ItemCategory> makeItemCategoryList(ResultSet rs) throws SQLException {
        List<ItemCategory> categories = new ArrayList<ItemCategory>();
        while (rs.next()) {
            categories.add(makeItemCategory(rs));
        }
        return categories;
    }

    // Public

    /**
     * Returns a list of all the item categories
     * that exist in the database and satisfy the
     * given filtering rules.
     *
     * @param  filter   specifies filtering rules
     * @return          list of categories
     * @throws          DataAccessException
     */
    public List<ItemCategory> getCategories(ItemCategoryFilter filter)
            throws DataAccessException {
        try {
            Connection conn = getConnection();
            PreparedStatement ps = CatalogDBQuery.getCategories(filter, conn);
            ResultSet rs = ps.executeQuery();
            List<ItemCategory> categories = makeItemCategoryList(rs);
            close(rs, ps, conn);
            return categories;
        } catch (SQLException e) {
            throw new DataAccessException();
        }
    }

    /**
     * Returns a list of all the items
     * that exist in the database and satisfy the
     * given filtering rules.
     *
     * @param  filter   specifies filtering rules
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
        	DataAccessException ex = new DataAccessException();
        	ex.setStackTrace(e.getStackTrace());
        	throw ex;
        }
    }

    /**
     * Returns the category that corresponds to
     * the given category unique identifer.
     *
     * @param  id       the category unique identifer
     * @return          the corresponding category or null
     * @throws          DataAccessException
     */
    public ItemCategory getCategory(int id) throws DataAccessException {
        try {
            Connection conn = getConnection();
            PreparedStatement ps = CatalogDBQuery.getCategory(id, conn);
            ResultSet rs = ps.executeQuery();
            ItemCategory category = rs.next() ? makeItemCategory(rs) : null;
            close(rs, ps, conn);
            return category;
        } catch (SQLException e) {
            throw new DataAccessException();
        }
    }

    /**
     * Returns the item that corresponds to
     * the given item unique identifer.
     *
     * @param  number   the item unique identifer
     * @return          the corresponding item or null
     * @throws          DataAccessException
     */
    public Item getItem(String number) throws DataAccessException {
        try {
            Connection conn = getConnection();
            PreparedStatement ps = CatalogDBQuery.getItem(number, conn);
            ResultSet rs = ps.executeQuery();
            Item item = rs.next() ? makeItem(rs) : null;
            close(rs, ps, conn);
            return item;
        } catch (SQLException e) {
            throw new DataAccessException();
        }
    }

} // CatalogDBAO
