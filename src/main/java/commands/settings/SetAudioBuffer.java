package commands.settings;

import commands.handling.CommandInfo;
import commands.handling.CommandInterface;
import commands.handling.CommandType;
import commands.handling.SecurityLevel;
import commands.music_handling.GuildPlayer;
import commands.music_handling.GuildPlayerManager;
import data.DATA;
import messages.MsgBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;
import tools.Toolkit;

import java.util.List;

public class SetAudioBuffer implements CommandInterface {
    @Override
    public String invoke() {
        return "setaudiobuffer";
    }

    @Override
    public boolean silent() {
        return false;
    }

    @Override
    public CommandType type() {
        return CommandType.MUSIC;
    }

    @Override
    public SecurityLevel securityLevel() {
        return SecurityLevel.BOT;
    }

    @Override
    public void run(CommandInfo info) {
        //Get all Values
        Integer duration = Toolkit.getInteger(info.getArgument(1));

        //Report wrong syntax
        if (duration == null) {
            info.wrongSyntax();
            return;
        }

        //The actual command
        //Convert duration from seconds to milliseconds
        duration = duration * 1000;
        DATA.bot().setFrameBufferDuration(duration);
        List<GuildPlayer> players = GuildPlayerManager.getAll();
        for (GuildPlayer player : players) {
            player.setFrameBufferDuration(duration);
        }
        //Notify member
        MessageEmbed embed = MsgBuilder.setAudioBuffer(duration);
        info.getChannel().sendMessage(embed).queue();
    }

    @Override
    public String title() {
        return "Stellt den Audio Buffer ein";
    }

    @Override
    public String description() {
        return "Stellt den Audio Buffer in Sekunden ein";
    }

    @Override
    public String syntax(String p) {
        return p + "setaudiobuffer < natürliche Zahl >";
    }
}
