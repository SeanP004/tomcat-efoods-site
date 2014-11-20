package model.checkout;

import java.io.*;

class PurchaseOrderFilter implements FilenameFilter {
    String regex;

    public PurchaseOrderFilter (String regex) {
        this.regex = regex;
    }

    public boolean accept(File dir, String name) {
        return name.matches(regex);
    }
}