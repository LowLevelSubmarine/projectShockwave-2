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

    public static String deleteAll(String a, String... b) {
        for (String c : b) {
            a = a.replaceAll(c, "");
        }
        return a;
    }

    public static boolean contains(String a, String... b) {
        for (String c : b) {
            if (!a.contains(c)) return false;
        }
        return true;
    }

    public static String removeInOut(String a, String b, String c) {
        while (contains(a, b, c)) {
            int in = a.indexOf(b);
            int out = a.indexOf(c);
            a = a.replace(a.substring(in, out + 1), "");
        }
        return a;
    }

    public static String removeLastIfIs(String a, String b) {
        if (a.substring(a.length() - 1, a.length()).equals(b)) {
            a = a.substring(0, a.length() - 1);
        }
        return a;
    }

    public static boolean isInBetween(int a, int b, int c) {
        return a >= b && a <= c;
    }

    public static int lastUnrestrictedIndexOf(String a, int max, String b) {
        if (a.length() < max) max = a.length();
        a = a.substring(0, max);
        int x = a.lastIndexOf(b);
        if (x == -1) return max;
        else return x;
    }

    public static String[] splitStringAt(String a, int b) {
        String[] x = {a.substring(0, b), a.substring(b, a.length())};
        return x;
    }

    public static ArrayList<String> getMaxStringsSplittedAt(String a, int max, String b) {
        ArrayList<String> x = new ArrayList<>();
        while (a.length() > max) {
            String[] s = splitStringAt(a, lastUnrestrictedIndexOf(a, max, b));
            a = s[1].substring(b.length());
            x.add(s[0]);
        }
        x.add(a);
        return x;
    }
}
