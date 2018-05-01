package commands.information;

import commands.CommandInfo;
import commands.CommandInterface;
import commands.SecurityLevel;
import messages.MsgBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;

public class Permissions implements CommandInterface {
    @Override
    public SecurityLevel securityLevel() {
        return SecurityLevel.NONE;
    }

    @Override
    public void run(CommandInfo info) {
        //Send message
        MessageEmbed embed = MsgBuilder.permissions();
        info.getChannel().sendMessage(embed).queue();
    }

    @Override
    public String title() {
        return "Infos zu den Berechtigungen";
    }

    @Override
    public String description() {
        return "Gibt ausführliche Informationen zum Berechtigungssystem";
    }

    @Override
    public String syntax(String p) {
        return p + "permissions";
    }
}
