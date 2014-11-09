package model;

import java.util.*;

public interface DAO {
    List<ItemBean>     getItems(GetItemOption options) throws Exception;
    List<CategoryBean> getCategories(GetCategoryOption options) throws Exception;
    ItemBean           getItemById(String id) throws Exception;
}

class GetItemOption {
    String  orderBy    = "name";  // Ordered by the item name
    int     offset     = -1;      // No offset set
    int     fetch      = -1;      // Unlimited page size
    String  category   = null;    // No category filter
    String  searchTerm = null;    // No search filter
    double  minPrice   = -1;      // No minimum price filter
    double  maxPrice   = -1;      // No maximum price filter
}

class GetCategoryOption {
    String  orderBy    = "name";  // Ordered by the category name
    int     offset     = -1;      // No offset set
    int     fetch      = -1;      // Unlimited page size
    String  searchTerm = null;    // No search filter
}
