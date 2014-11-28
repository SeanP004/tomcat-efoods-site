package model.common;

public class CommonUtil {

    private CommonUtil() {} // no constructor

    /**
     * Round the given double value to two decimal points, for money.
     *
     * @param x double value
     * @return rounded double value
     */
    public static double roundOff(double x) {
        long val = Math.round(x * 100); // cents
        return val / 100.0;
    }

} // CommonUtil
