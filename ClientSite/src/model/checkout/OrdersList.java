package model.checkout;

import java.io.*;
import java.util.*;
import javax.xml.bind.annotation.*;

@XmlRootElement(name="orders")
public class OrdersList {
    
    private String prefix;
    private File[] files;
    
    public OrdersList() { } // To make JAXB happy
    
    public OrdersList(String prefix, File[] files) {
        this.prefix = prefix;
        this.files = files;
    }

    @XmlElement(name="file")
    public List<String> getFiles() {
        List<String> fileNames = new ArrayList<String>();
        for (File f : files) {
            fileNames.add(prefix + f.getName());
        }
        return fileNames;
    }
} // OrdersList
