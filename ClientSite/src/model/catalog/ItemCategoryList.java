package model.catalog;

import javax.xml.bind.annotation.*;
import java.util.*;

@XmlRootElement(name="collection")
@XmlType(propOrder={"filter", "categories"})
public class ItemCategoryList {

    private List<ItemCategory> categories;
    private ItemCategoryFilter filter;

    public ItemCategoryList() {}
    public ItemCategoryList(List<ItemCategory> categories, ItemCategoryFilter filter) {
        this.categories = categories;
        this.filter     = filter;
    }

    // Getters

    @XmlAttribute
    public int getSize() {
        return categories.size();
    }
    
    @XmlElement
    public ItemCategoryFilter getFilter() {
        return filter;
    }
    
    @XmlElementWrapper(name="categories")
    @XmlElement(name="category")
    public List<ItemCategory> getCategories() {
        return categories;
    }

} // ItemCategoryList
