package model.catalog;

/**
 * Object for filtering the item category query.
 * This object is created for each query to
 * get multiple items from the category relation.
 * Faciliates a multitude of search and filtering
 * combinations, including pagination and ordering.
 */
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
        this.orderBy = orderBy;
    }
    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
    }
    public void setOffset(int offset) {
        this.offset = offset;
    }
    public void setFetch(int fetch) {
        this.fetch = fetch;
    }

    public void setPagination(int offset, int fetch) {
        setOffset(offset);
        setFetch(fetch);
    }

} // ItemCategoryFilter
