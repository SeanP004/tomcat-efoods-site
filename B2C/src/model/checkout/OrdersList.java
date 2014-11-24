package model.checkout;

import java.io.*;
import java.util.*;
import javax.xml.bind.annotation.*;

@XmlRootElement(name="orders")
public class OrdersList {
    
    private String prefix;
    private File[] files;
    private String account;

    public OrdersList() { } // To make JAXB happy
    
    public OrdersList(String prefix, File[] files) {
        this.prefix = prefix;
        this.files = files;
    }
    
    public OrdersList(String prefix, File[] files, String account) {
        this(prefix, files);
        setAccount(account);
    }

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

    public void setAccount(String account) {
        this.account = account;
    }

} // OrdersList
