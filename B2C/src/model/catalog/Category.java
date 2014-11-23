package model.catalog;

import javax.xml.bind.annotation.*;

/**
 * Simple representation of a catalog category.
 * Corresponds to a record in the category relation
 * within the catalog database.
 */
@XmlRootElement(name="category")
@XmlType(propOrder={"id", "name", "description", "picture"})
public class Category {

    private int    id;
    private String name;
    private String description;
    private byte[] picture;

    // Getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public byte[] getPicture() {
        return picture;
    }

    // Setters

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPicture(byte[] picture) { // base64 encoded
        this.picture = picture;
    }

} // Category
