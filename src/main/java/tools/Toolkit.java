package tools;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

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
    public static boolean isOneOf(String a, String... b) {
        for (String c : b) {
            if (a.equals(c)) return true;
        }
        return false;
    }
    public static String[] removeEmptyString(String[] strs) {
        ArrayList<String> strList= new ArrayList<>();
        for(String str : strs) {
            if (isEmpty(str)) {
                strList.add(str);
            }
        }
        return strList.toArray(new String[0]);
    }

    public static boolean isEmpty(String str) {
        return str == null || str.equals("");
    }

    public static boolean isBoolean(String str) {
        str = str.toLowerCase();
        return str.equals("true") || str.equals("false");
    }

    public static String spacer(int length, char fill) {
        String out = "";
        while (length > 0) {
            out += fill;
            length--;
        }
        return out;
    }
    public static String trim(String string, char fill, int size) {
        final int length = string.length();
        if(length < size) {
            for (int loop = 0; loop < size - length; loop++) {
                string += fill;
            }
        } else if(length > size) {
            string = string.substring(0, size);
        }
        return string;
    }
    public static String align(String string, char fill, int size, Enum align) {
        final int rawLength = string.length();
        switch (align.toString()) {
            default:
            case "LEFT":
                if(rawLength < size) {
                    for (int loop = 0; loop < size - rawLength; loop++) {
                        string += fill;
                    }
                } else if(rawLength > size) {
                    string = string.substring(0, size);
                }
                break;
            case "RIGHT":
                if(rawLength < size) {
                    String content = string;
                    string = "";
                    for (int loop = 0; loop < size - rawLength; loop++) {
                        string += fill;
                    }
                    string += content;
                } else if(rawLength > size) {
                    string = string.substring(0, size);
                }
                break;
            case "CENTER":
                if(rawLength < size) {
                    int front = (size - rawLength) / 2;
                    int back = size - (front + rawLength);
                    String content = string;
                    string = "";
                    for (int loop = 0; loop < front; loop++) {
                        string += fill;
                    }
                    string += content;
                    for (int loop = 0; loop < back; loop++) {
                        string += fill;
                    }
                } else if(rawLength > size) {
                    string = string.substring(0, size);
                }
                break;
        }
        return string;
    }

    public static String booleanToString(boolean bool) {
        if (bool) {
            return "Y";
        } else {
            return "N";
        }
    }

    public static int limit(int value, int min, int max) {
        if (value > max) {
            return max;
        } else if (value < min) {
            return min;
        } else {
            return value;
        }
    }

    public static Integer getInteger(String string) {
        try {
            return Integer.parseInt(string);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static boolean startsWith(String a, String... b) {
        for (String c : b) {
            if (a.startsWith(c)) return true;
        }
        return false;
    }
}
