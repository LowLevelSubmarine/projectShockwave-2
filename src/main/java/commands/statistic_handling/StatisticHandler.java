package commands.statistic_handling;

import core.ExceptionLogger;
import data.DATA;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;

public class StatisticHandler {

    private static final String FOLDERNAME = "Statistics";
    private static ArrayList<StatisticInterface> STATISTICS = new ArrayList<>();
    private static StatisticContainer CONTAINER = new StatisticContainer();
    private static Timer THREAD = new Timer();

    public static void boot() {
        CONTAINER = DATA.bot().getStatisticContainer();
        THREAD.scheduleAtFixedRate(new StatisticThread(), 0, 30000);
    }
    public static void shutdown() {
        THREAD.cancel();
        DATA.bot().setStatisticContainer(CONTAINER);
    }
    public static void save() {
        DATA.bot().setStatisticContainer(CONTAINER);
    }
    public static void addStatistic(StatisticInterface statInterface) {
        STATISTICS.add(statInterface);
    }
    public static File renderStatistics() {
        String rendered = "";
        for(StatisticInterface statInterface : STATISTICS) {
            rendered += statInterface.title() + "\n\n" + statInterface.render() + "\n\n\n\n\n";
        }
        File folder = new File(FOLDERNAME);
        folder.mkdir();
        File file = new File(FOLDERNAME + "/" + System.currentTimeMillis() + ".txt");
        try {
            file.createNewFile();
            FileWriter writer = new FileWriter(file);
            writer.write(rendered);
            writer.close();
        } catch (IOException e) {
            ExceptionLogger.log(e);
            return null;
        }
        return file;
    }
}
