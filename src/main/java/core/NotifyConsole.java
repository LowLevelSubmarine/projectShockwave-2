package core;

import database.DATA;

import java.time.LocalDateTime;

public class NotifyConsole {
    public static void log(String author, String text) {
        print("log", author, text);
    }
    public static void debug(String author, String text) {
        if (DATA.config().debugMode()) {
            print("debug", author, text);
        }
    }

    private static void print(String type, String author, String text) {
        String timeSection = "[" + getTimeOfYear() + "]";
        String typeSection = "[" + type.toUpperCase() + "]";
        String authorSection = "[" + author.toUpperCase() + "]";
        System.out.println(timeSection + " " + typeSection + " " + authorSection + " " + text);
    }
    private static String getTimeOfYear() {
        LocalDateTime currentTime = LocalDateTime.now();
        String day = String.format("%02d", currentTime.getDayOfMonth());
        String month = String.format("%02d", currentTime.getMonthValue());
        String hour = String.format("%02d", currentTime.getHour());
        String minute = String.format("%02d", currentTime.getMinute());
        return day + "." + month + "-" + hour + ":" + minute;
    }
}