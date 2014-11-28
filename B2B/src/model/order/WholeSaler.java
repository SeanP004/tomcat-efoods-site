package model.order;

import java.util.HashMap;

public class WholeSaler {
    public static String WTORONTO;
    public static String WVANCOUVER;
    public static String WHALIFAX;
    
    public WholeSaler (HashMap<String, String> config) {
        this.WTORONTO = config.get("WTORONTO");
        this.WVANCOUVER = config.get("WVANCOUVER");
        this.WHALIFAX = config.get("WHALIFAX");
    }
}
