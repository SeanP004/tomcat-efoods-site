package model.catalog;

import java.util.*;
import javax.xml.bind.annotation.XmlRootElement;
import model.exception.*;

/**
 * Object for filtering the item category query. This object is created for each
 * query to get multiple items from the category relation. Faciliates a
 * multitude of search and filtering combinations, including pagination and
 * ordering.
 */
@XmlRootElement(name="filter")
public class ItemCategoryFilter {

    private String orderBy    = null;
    private String searchTerm = null;
    private int    offset     = -1;
    private int    fetch      = -1;

    // Getters

    public String getOrderBy() {
        return orderBy;
    }

    public String getSearchTerm() {
        return searchTerm;
    }

    public int getOffset() {
        return offset;
    }

    public int getFetch() {
        return fetch;
    }

    // Setters

    public void setOrderBy(String orderBy) {
        if (!hasOrder(orderBy)) { throw new InvalidQueryFilterException(
                ERR_ORDERBY); }
        this.orderBy = orderBy;
    }

    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
    }

    public void setOffset(int offset) {
        if (offset < 0) { throw new InvalidQueryFilterException(ERR_OFFSET); }
        this.offset = offset;
    }

    public void setFetch(int fetch) {
        if (fetch <= 0) { throw new InvalidQueryFilterException(ERR_FETCH); }
        this.fetch = fetch;
    }

    public void setPagination(int offset, int fetch) {
        setOffset(offset);
        setFetch(fetch);
    }

    // Static

    private static final String
        ERR_ORDERBY = "Invalid orderBy argument."
      , ERR_OFFSET  = "Cannot have negative offset."
      , ERR_FETCH   = "Cannot have non-positive fetch."
      ;

    private static final List<String> sorts;
    static {
        sorts = new ArrayList<String>();
        sorts.add("id");
        sorts.add("name");
    }

    private static boolean hasOrder(String order) {
        return sorts.contains(order);
    }

} // ItemCategoryFilter
