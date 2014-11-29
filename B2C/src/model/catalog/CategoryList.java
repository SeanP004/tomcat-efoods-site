package model.catalog;

import javax.xml.bind.annotation.*;
import java.util.*;

/**
 * 
 * CategoryList store an list of object category
 *
 */
@XmlRootElement(name="collection")
@XmlType(propOrder={"filter", "categories"})
public class CategoryList {

    private List<Category> categories;
    private CategoryFilter filter;

    public CategoryList() {}
    public CategoryList(List<Category> categories, CategoryFilter filter) {
        this.categories = categories;
        this.filter     = filter;
    }

    // Getters

    @XmlAttribute
    public int getSize() {
        return categories.size();
    }

    @XmlElement
    public CategoryFilter getFilter() {
        return filter;
    }

    @XmlElementWrapper(name="categories")
    @XmlElement(name="category")
    public List<Category> getCategories() {
        return categories;
    }

} // CategoryList
