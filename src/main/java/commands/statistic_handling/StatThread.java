package commands.statistic_handling;

import java.util.TimerTask;

public class StatThread extends TimerTask {
    @Override
    public void run() {
        StatHandler.save();
    }
}
