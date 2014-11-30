package model.account;

import javax.xml.bind.annotation.*;

/**
 * The Account object represents a logged customer
 * in the system. Holds information about the
 * user's account information, including id,
 * name and avatar.
 */
@XmlRootElement(name="customer")
public class Account {

    private String id;
    private String name;
    private String avatar; // user picture

    public Account() { } // To make JAXB happy

    /**
     * Constructs a new Account object
     *
     * @param id        the username (id)
     * @param name      the name of the user
     * @param avatar    URI to profile picture
     */
    public Account(String id, String name, String avatar) {
        this.id     = id;
        this.name   = name;
        this.avatar = avatar;
    }

    // Getters

    @XmlAttribute(name="accountID")
    public String getId() {
        return id;
    }

    @XmlElement(name="accountName")
    public String getName() {
        return name;
    }

    public String getAvatar() {
        return avatar;
    }

} // User
