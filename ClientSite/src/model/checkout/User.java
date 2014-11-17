package model.checkout;

import javax.xml.bind.annotation.*;

/**
 * The User object represents a logged customer 
 * in the system. Holds information about the
 * user's account information, including id,
 * name and avatar.
 */
@XmlRootElement(name="customer")
public class User {

    private String id;
    private String name;
    private String avatar; // user picture
    
    public User() { } // To make JAXB happy

    /**
     * Constructs a new User object
     * 
     * @param id    the username (id)
     * @param name  the name of the user
     */
    public User(String id, String name) {
        setId(id);
        setName(name);
    }

    // Getters    

    @XmlAttribute(name="account")
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAvatar() {
        return avatar;
    }

    // Setters

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
    
} // User
