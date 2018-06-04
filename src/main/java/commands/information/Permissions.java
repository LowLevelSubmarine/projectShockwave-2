package commands.information;

import commands.handling.CommandInfo;
import commands.handling.CommandInterface;
import commands.handling.CommandType;
import commands.handling.SecurityLevel;
import messages.MsgBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.PrivateChannel;

public class Permissions implements CommandInterface {
    @Override
    public String invoke() {
        return "permissions";
    }

    @Override
    public boolean silent() {
        return false;
    }

    @Override
    public CommandType type() {
        return CommandType.INFORMATION;
    }

    @Override
    public SecurityLevel securityLevel() {
        return SecurityLevel.NONE;
    }

    @Override
    public void run(CommandInfo info) {
        //Send message
        MessageEmbed embed = MsgBuilder.permissions();
        PrivateChannel privateChannel = info.getUser().openPrivateChannel().complete();
        privateChannel.sendMessage(embed).queue();
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
