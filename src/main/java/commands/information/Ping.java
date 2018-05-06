package commands.information;

import commands.CommandInfo;
import commands.CommandInterface;
import commands.SecurityLevel;
import messages.MsgBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.TextChannel;

public class Ping implements CommandInterface {
    @Override
    public SecurityLevel securityLevel() {
        return SecurityLevel.NONE;
    }

    @Override
    public void run(CommandInfo info) {
        long ping = info.getJDA().getPing();
        TextChannel textChannel = info.getChannel();
        MessageEmbed embed = MsgBuilder.ping(ping);
        textChannel.sendMessage(embed).queue();
    }

    @Override
    public String category() {
        return "Infos";
    }

    @Override
    public String title() {
        return "Gibt den Ping an";
    }

    @Override
    public String description() {
        return "Gibt den Ping der Discord API an";
    }

    @Override
    public String syntax(String p) {
        return p + "ping";
    }
}
