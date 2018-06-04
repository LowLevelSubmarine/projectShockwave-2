package commands.settings;

import commands.handling.CommandInfo;
import commands.handling.CommandInterface;
import commands.handling.CommandType;
import commands.handling.SecurityLevel;
import data.DATA;
import messages.MsgBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.TextChannel;

import java.util.List;

public class SetMusicChannel implements CommandInterface {
    @Override
    public String invoke() {
        return "setmusicchannel";
    }

    @Override
    public boolean silent() {
        return false;
    }

    @Override
    public CommandType type() {
        return CommandType.SETTINGS;
    }

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
        DATA.guild(info.getGuild()).setMusicChannel(mentionedChannels.get(0));
        MessageEmbed embed = MsgBuilder.setMusicChannelDone(mentionedChannels.get(0));
        info.getChannel().sendMessage(embed).queue();
    }

    @Override
    public String title() {
        return "Setzt den Musikkanal";
    }

    @Override
    public String description() {
        return "Setzt den Textchannel in welchem die Queue angezeigt wird";
    }

    @Override
    public String syntax(String p) {
        return p + "setmusicchannel < #Textchannel >";
    }
}
