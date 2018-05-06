package core;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Toolkit {
    public static boolean getAsBoolean(String string) {
        return getAsBoolean(string, "true");
    }
    public static boolean getAsBoolean(String string, String caseTrue) {
        return string.toLowerCase().equals(caseTrue.toLowerCase());
    }
    //Credits to stackoverflow-user "Jonik"
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
    public static String trimString(String string, int maxLength) {
        if (string.length() > maxLength) {
            return string.substring(0, maxLength);
        }
        return string;
    }
}
