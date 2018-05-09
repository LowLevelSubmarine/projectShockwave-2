package commands.administration;

import commands.CommandInfo;
import commands.CommandInterface;
import commands.SecurityLevel;
import commands.administration.statistics.StatisticHandler;

import java.io.File;

public class Statistics implements CommandInterface {

    @Override
    public SecurityLevel securityLevel() {
        return SecurityLevel.BOT;
    }

    @Override
    public void run(CommandInfo info) {
        File file = StatisticHandler.renderStatistics();
        if (file != null) info.getChannel().sendFile(file).queue();
    }

    @Override
    public String category() {
        return "Administration";
    }

    @Override
    public String title() {
        return "Viele viele Zahlen";
    }

    @Override
    public String description() {
        return "Schickt eine Textdatei mit einigen gesammelten Informationen";
    }

    @Override
    public String syntax(String p) {
        return p + "statistics";
    }
}
