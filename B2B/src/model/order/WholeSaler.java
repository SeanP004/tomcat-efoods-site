package model.order;

import java.util.HashMap;

public class WholeSaler {
    public final String WTORONTO;
    public final String WVANCOUVER;
    public final String WHALIFAX;

    public WholeSaler (HashMap<String, String> config) {
        this.WTORONTO = config.get("WTORONTO");
        this.WVANCOUVER = config.get("WVANCOUVER");
        this.WHALIFAX = config.get("WHALIFAX");
    }
}
