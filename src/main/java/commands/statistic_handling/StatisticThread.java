package commands.statistic_handling;

import core.NotifyConsole;

import java.util.TimerTask;

public class StatisticThread extends TimerTask {
    @Override
    public void run() {
        StatisticHandler.save();
        NotifyConsole.debug(StatisticThread.class, "Fired save method");
    }
}
