package model.checkout;

import java.io.*;
import java.util.*;
import javax.xml.bind.annotation.*;

/**
 * 
 * OrderList stores the list of file that contains the order
 * that is assocated by the account
 *
 */
@XmlRootElement(name="orders")
public class OrdersList {
    
    private String prefix;
    private File[] files;
    private String account;

    public OrdersList() { } // To make JAXB happy
    
    /**
     * Order List constructor
     * 
     * @param prefix 	Sting value of prefix
     * @param files		A list of file
     */
    public OrdersList(String prefix, File[] files) {
        this.prefix = prefix;
        this.files = files;
    }
    
    /**
     * Order List constructor
     * 
     * @param prefix 	Sting value of prefix
     * @param files		A list of file
     * @param account 	a account string value name
     */
    public OrdersList(String prefix, File[] files, String account) {
        this(prefix, files);
        setAccount(account);
    }

    //Getter
    
    @XmlElement(name="file")
    public List<String> getFiles() {
        List<String> fileNames = new ArrayList<String>();
        Arrays.sort(files);
        for (File f : files) {
            fileNames.add(prefix + f.getName());
        }
        return fileNames;
    }
    
    @XmlAttribute
    public String getAccount() {
        return account;
    }
    
    //Setter

    public void setAccount(String account) {
        this.account = account;
    }

} // OrdersList
