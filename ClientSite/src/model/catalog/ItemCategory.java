package model.catalog;

/**
 * Simple representation of a catalog category.
 * Corresponds to a record in the category relation
 * within the catalog database.
 */
public class ItemCategory {

    private int id;
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

} // ItemCategory
