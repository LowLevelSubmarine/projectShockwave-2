package commands.information;

import commands.handling.CommandInfo;
import commands.handling.CommandInterface;
import commands.handling.CommandType;
import commands.handling.SecurityLevel;
import core.JDAHandler;
import core.Statics;
import data.DATA;
import messages.MsgBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.User;

public class Info implements CommandInterface {
    @Override
    public String invoke() {
        return "info";
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
        MessageEmbed embed = MsgBuilder.info();
        info.getChannel().sendMessage(embed).queue();
    }

    @Override
    public String title() {
        return "Informationen zum Bot";
    }

    @Override
    public String description() {
        return "Informationen zum Bot";
    }

    @Override
    public String syntax(String p) {
        return p + "info";
    }
}
