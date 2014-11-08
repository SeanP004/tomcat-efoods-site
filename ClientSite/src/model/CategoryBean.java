package model;

public class CategoryBean {

    private int ID;
    private String name;
    private String description;
    private String picture;

    // Setters

    public void setID         (int ID)             {this.ID = ID;}
    public void setName       (String name)        {this.name = name;}
    public void setDescription(String description) {this.description = description;}
    public void setPicture    (String picture)     {this.picture = picture;}

    // Getters

    public int    getID()          {return ID;}
    public String getName()        {return name;}
    public String getDescription() {return description;}
    public String getPicture()     {return picture;}

} // CategoryBean
