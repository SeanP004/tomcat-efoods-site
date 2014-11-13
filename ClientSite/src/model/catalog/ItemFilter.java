package model.catalog;

/**
 * Object for filtering the items query.
 * This object is created for each query to
 * get multiple items from the item relation.
 * Faciliates a multitude of search and filtering
 * combinations, including pagination and ordering.
 */
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
        this.orderBy = orderBy;
    }
    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
    }
    public void setCategory(int category) {
        this.category = category;
    }
    public void setOffset(int offset) {
        this.offset = offset;
    }
    public void setFetch(int fetch) {
        this.fetch = fetch;
    }
    public void setMinPrice(double minPrice) {
        this.minPrice = minPrice;
    }
    public void setMaxPrice(double maxPrice) {
        this.maxPrice = maxPrice;
    }

    public void setCategory(ItemCategory category) {
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

} // ItemFilter
