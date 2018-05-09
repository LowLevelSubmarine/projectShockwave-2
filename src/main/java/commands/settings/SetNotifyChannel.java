package commands.settings;

import commands.CommandInfo;
import commands.CommandInterface;
import commands.SecurityLevel;
import database.DATA;
import messages.MsgBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.TextChannel;

import java.util.List;

public class SetNotifyChannel implements CommandInterface {

    @Override
    public SecurityLevel securityLevel() {
        return SecurityLevel.GUILD;
    }

    @Override
    public void run(CommandInfo info) {
        List<TextChannel> mentionedChannels = info.getMessage().getMentionedChannels();
        if (mentionedChannels.size() != 1) {
            info.wrongSyntax();
            return;
        }
        DATA.guild(info.getGuild()).setNotifyChannel(mentionedChannels.get(0));
        MessageEmbed embed = MsgBuilder.setNotifyChannelDone(mentionedChannels.get(0));
        info.getChannel().sendMessage(embed).queue();
    }

    @Override
    public String category() {
        return "Server";
    }

    @Override
    public String title() {
        return "Legt den Benachrichtigungskanal fest";
    }

    @Override
    public String description() {
        return "Legt den Kanal fest in welchen der Bot Benachrichtigungen schickt die keine Antworten auf Usernachrichten sind";
    }

    @Override
    public String syntax(String p) {
        return p + "setnotifychannel < #mention >";
    }
}
