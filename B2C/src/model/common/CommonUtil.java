package model.common;

import java.io.UnsupportedEncodingException;
import java.security.*;

public class CommonUtil {

    private CommonUtil() { } // no constructor

    /**
     * Round the given double value to
     * two decimal points, for money.
     *
     * @param  x double value
     * @return   rounded double value
     */
    public static double roundOff(double x) {
        long val = Math.round(x*100); // cents
        return val/100.0;
    }

    /**
     * Represent a byte array as a Hex string.
     * 
     * @param ar    the array of bytes
     * @return      the string representation in hex
     */
    public static String byteToHex(byte[] ar) {
        assert ar != null;
        String result = "";
        for (int i = 0; i < ar.length; i++) {
            int x = ar[i] & 0x000000FF;
            if (x < 16) {
                result += "0";
            }
            result += Integer.toHexString(x);
        }
        return result.toUpperCase();
    }

    /**
     * Given a string of data return the
     * corresponding MD5 Hash string.
     * 
     * @param data  to be hashed
     * @return      the hash
     * 
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    public static String md5sum(String data)
            throws NoSuchAlgorithmException, 
                   UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        return byteToHex(md.digest(data.getBytes("UTF-8"))).toLowerCase();
    }

} // CommonUtil
