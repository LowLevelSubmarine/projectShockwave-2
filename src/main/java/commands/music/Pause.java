package commands.music;

import commands.handling.CommandInfo;
import commands.handling.CommandInterface;
import commands.handling.CommandType;
import commands.handling.SecurityLevel;
import commands.music_handling.GuildPlayer;
import commands.music_handling.GuildPlayerManager;

public class Pause implements CommandInterface {
    @Override
    public String invoke() {
        return "pause";
    }

    @Override
    public boolean silent() {
        return true;
    }

    @Override
    public CommandType type() {
        return CommandType.MUSIC;
    }

    @Override
    public SecurityLevel securityLevel() {
        return SecurityLevel.NONE;
    }

    @Override
    public void run(CommandInfo info) {
        if (GuildPlayerManager.has(info.getGuild())) {
            GuildPlayer player = GuildPlayerManager.get(info.getGuild());
            if (player.isPlaying()) {
                player.setPaused(!player.isPaused());
            }
        }
    }

    @Override
    public String title() {
        return "Pausiert die Wiedergabe";
    }

    @Override
    public String description() {
        return "Pausiert die Wiedergabe, falls Musik abgespielt wird.";
    }

    @Override
    public String syntax(String p) {
        return p + "pause";
    }
}
