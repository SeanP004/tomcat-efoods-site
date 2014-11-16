package model.database;

import model.exception.*;
import model.catalog.*;
import java.util.*;

/**
 * Abstract interface of the Catalog database. Allows the application to query
 * for a list of categories or items, or an individual category or item given
 * its unique identifier.
 *
 * This abstraction allows the implementators to change the source and specifics
 * about the the data sources without changing the interface of the catalog data
 * access. Allows for multiple implementations.
 */
public interface CatalogDB {

    /**
     * Returns a list of all the categories that exist in the database and
     * satisfy the given filtering rules.
     *
     * @param filter    specifies filtering rules
     * @return          list of categories
     * @throws          DataAccessException
     */
    List<Category> getCategories(CategoryFilter filter)
            throws DataAccessException;

    /**
     * Returns a list of all the items that exist in the database and satisfy
     * the given filtering rules.
     *
     * @param filter    specifies filtering rules
     * @return          list of items
     * @throws          DataAccessException
     */
    List<Item> getItems(ItemFilter filter) throws DataAccessException;

    /**
     * Returns the category that corresponds to the given category unique
     * identifer.
     *
     * @param id    the category unique identifer
     * @return      the corresponding category or null
     * @throws      DataAccessException , CategoryNotFoundException
     */
    Category getCategory(int id) throws DataAccessException,
            CategoryNotFoundException;

    /**
     * Returns the item that corresponds to the given item unique identifer.
     *
     * @param id    the item unique identifer
     * @return      the corresponding item or null
     * @throws      DataAccessException , ItemNotFoundException
     */
    Item getItem(String number) throws DataAccessException,
            ItemNotFoundException;

} // CatalogDB
