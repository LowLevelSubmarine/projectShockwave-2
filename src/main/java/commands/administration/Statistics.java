package commands.administration;

import commands.handling.CommandInfo;
import commands.handling.CommandInterface;
import commands.handling.CommandType;
import commands.handling.SecurityLevel;
import commands.statistic_handling.StatHandler;

import java.io.File;

public class Statistics implements CommandInterface {

    @Override
    public String invoke() {
        return "statistics";
    }

    @Override
    public CommandType type() {
        return CommandType.ADMINISTRATION;
    }

    @Override
    public SecurityLevel securityLevel() {
        return SecurityLevel.BOT;
    }

    @Override
    public void run(CommandInfo info) {
        File file = StatHandler.renderStatistics();
        if (file != null) info.getChannel().sendFile(file).queue();
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
