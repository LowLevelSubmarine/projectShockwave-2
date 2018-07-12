package commands.statistic_handling;

import core.ExceptionLogger;
import core.NotifyConsole;
import data.DATA;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;

public class StatHandler {

    private static final String FOLDERNAME = "Statistics";
    private static ArrayList<StatInterface> STATINTERFACES = new ArrayList<>();
    private static StatContainer STATCONTAINER = new StatContainer();
    private static Timer THREAD = new Timer();

    public static void boot() {
        load();
        THREAD.scheduleAtFixedRate(new StatThread(), 0, 30000);
    }
    public static void shutdown() {
        THREAD.cancel();
        save();
    }

    public static void save() {
        if (!DATA.bot().getStatisticContainer().equals(STATCONTAINER)) {
            DATA.bot().setStatisticContainer(STATCONTAINER);
            NotifyConsole.debug(StatHandler.class, "Saved changes");
        }
    }
    public static void load() {
        STATCONTAINER = DATA.bot().getStatisticContainer();
    }

    public static void addStatInterface(StatInterface statInterface) {
        STATINTERFACES.add(statInterface);

    }
    public static File renderStatistics() {
        String rendered = "";
        for(StatInterface statInterface : STATINTERFACES) {
            rendered += statInterface.title() + "\n\n" + statInterface.render() + "\n\n\n\n\n";
        }
        File folder = DATA.createStatisticFolder(FOLDERNAME);
        File file = new File(folder.getPath() + "/" + System.currentTimeMillis() + ".html");
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
