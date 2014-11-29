package model.catalog;

import javax.xml.bind.annotation.*;
import java.util.*;

/**
 * 
 * ItemList store an list of object item
 *
 */
@XmlRootElement(name="collection")
@XmlType(propOrder={"filter", "items"})
public class ItemList {

    private List<Item> items;
    private ItemFilter filter;

    public ItemList() {}
    public ItemList(List<Item> items, ItemFilter filter) {
        this.items  = items;
        this.filter = filter;
    }

    // Getters

    @XmlAttribute
    public int getSize() {
        return items.size();
    }
    
    @XmlElement
    public ItemFilter getFilter() {
        return filter;
    }
    
    @XmlElementWrapper(name="items")
    @XmlElement(name="item")
    public List<Item> getItems() {
        return items;
    }

} // ItemList
