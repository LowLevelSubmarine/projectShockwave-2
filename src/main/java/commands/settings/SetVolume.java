package commands.settings;

import commands.handling.CommandInfo;
import commands.handling.CommandInterface;
import commands.handling.CommandType;
import commands.handling.SecurityLevel;
import commands.music_handling.PlayerHandler;
import commands.music_handling.GuildPlayer;
import core.JDAHandler;
import data.DATA;
import messages.MsgBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;
import tools.Toolkit;

public class SetVolume implements CommandInterface {
    @Override
    public String invoke() {
        return "setvolume";
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
        //Get all Values
        Integer volume = Toolkit.getInteger(info.getArgument(1));

        //Report wrong syntax
        if (volume == null) {
            info.wrongSyntax();
            return;
        }

        //Do the math
        DATA.guild(info.getGuild()).setVolume(volume);
        GuildPlayer guildPlayer = PlayerHandler.getQueue(info.getGuild());
        if (guildPlayer != null) {
            guildPlayer.setVolume(volume);
        }
        MessageEmbed embed = MsgBuilder.setVolume(volume);
        info.getChannel().sendMessage(embed).queue();
    }

    @Override
    public String title() {
        return "Stellt die Lautstärke um";
    }

    @Override
    public String description() {
        return "Stellt die Lautstärke ein mit welcher " + JDAHandler.getUsername() + " Musik abspielt. Erlaubt sind alle Werte zwischen 0 und 200";
    }

    @Override
    public String syntax(String p) {
        return p + "setvolume < Lautstärke >";
    }
}
