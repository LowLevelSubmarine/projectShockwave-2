package core;

public class Toolkit {
    public static boolean getAsBoolean(String string) {
        return getAsBoolean(string, "true");
    }
    public static boolean getAsBoolean(String string, String caseTrue) {
        return string.toLowerCase().equals(caseTrue.toLowerCase());
    }
}
