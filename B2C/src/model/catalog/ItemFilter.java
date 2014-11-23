package model.catalog;

import java.util.*;
import javax.xml.bind.annotation.*;
import model.exception.*;

/**
 * Object for filtering the items query. This object is created for each query
 * to get multiple items from the item relation. Faciliates a multitude of
 * search and filtering combinations, including pagination and ordering.
 */
@XmlRootElement(name="filter")
public class ItemFilter {

    private String orderBy    = null;
    private String searchTerm = null;
    private int    category   = -1;
    private int    offset     = -1;
    private int    fetch      = -1;
    private double minPrice   = -1;
    private double maxPrice   = -1;

    // Getters

    public String getOrderBy() {
        return orderBy;
    }

    public String getSearchTerm() {
        return searchTerm;
    }

    public int getCategory() {
        return category;
    }

    public int getOffset() {
        return offset;
    }

    public int getFetch() {
        return fetch;
    }

    public double getMinPrice() {
        return minPrice;
    }

    public double getMaxPrice() {
        return maxPrice;
    }

    // Setters

    public void setOrderBy(String orderBy) {
        if (!hasOrder(orderBy)) { throw new InvalidQueryFilterException(ERR_ORDERBY); }
        this.orderBy = orderBy;
    }

    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public void setOffset(int offset) {
        if (offset < 0) { throw new InvalidQueryFilterException(ERR_OFFSET); }
        this.offset = offset;
    }

    public void setFetch(int fetch) {
        if (fetch <= 0) { throw new InvalidQueryFilterException(ERR_FETCH); }
        this.fetch = fetch;
    }

    public void setMinPrice(double minPrice) {
        if (minPrice < 0) { throw new InvalidQueryFilterException(ERR_MINPRICE); }
        if (maxPrice > 0 && maxPrice <= minPrice) {
            throw new InvalidQueryFilterException(ERR_PRICERANGE); }
        this.minPrice = minPrice;
    }

    public void setMaxPrice(double maxPrice) {
        if (maxPrice <= 0) { throw new InvalidQueryFilterException(ERR_MAXPRICE); }
        if (minPrice >= 0 && maxPrice <= minPrice) {
            throw new InvalidQueryFilterException(ERR_PRICERANGE); }
        this.maxPrice = maxPrice;
    }

    public void setCategory(Category category) {
        setCategory(category.getId());
    }

    public void setPagination(int offset, int fetch) {
        setOffset(offset);
        setFetch(fetch);
    }

    public void setPriceRange(double minPrice, double maxPrice) {
        setMinPrice(minPrice);
        setMaxPrice(maxPrice);
    }

    // Static

    private static final String
        ERR_ORDERBY    = "Invalid orderBy argument."
      , ERR_OFFSET     = "Cannot have negative offset."
      , ERR_FETCH      = "Cannot have non-positive fetch."
      , ERR_MINPRICE   = "Cannot have negative minPrice."
      , ERR_MAXPRICE   = "Cannot have non-positive maxPrice."
      , ERR_PRICERANGE = "minPrice cannot be equal or greater than maxPrice."
      ;

    public static final Map<String, String> sorts;
    static {
        sorts = new HashMap<String, String>();
        sorts.put("number", "Item Number");
        sorts.put("name", "Name");
        sorts.put("price", "Price");
        sorts.put("catid", "Category");
        //sorts.put("qty");
        //sorts.put("onorder");
        //sorts.put("reorder");
        //sorts.put("supid");
        //sorts.put("costprice");
        //sorts.put("unit");
    }

    private static boolean hasOrder(String order) {
        return sorts.containsKey(order);
    }

} // ItemFilter
