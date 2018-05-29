package commands.music;

import commands.handling.CommandInfo;
import commands.handling.CommandInterface;
import commands.handling.CommandType;
import commands.handling.SecurityLevel;
import commands.music_handling.GuildPlayer;
import commands.music_handling.GuildPlayerManager;

public class Loop implements CommandInterface {
    @Override
    public String invoke() {
        return "loop";
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
        if (GuildPlayerManager.hasGuildPlayer(info.getGuild())) {
            GuildPlayer gPlayer = GuildPlayerManager.getGuildPlayer(info.getGuild());
            gPlayer.switchLoopMode();
        }
    }

    @Override
    public String title() {
        return "Wiederholt den aktuellen Track";
    }

    @Override
    public String description() {
        return "Wiederholt den aktuellen Track bis der Command neu eingegeben wird";
    }

    @Override
    public String syntax(String p) {
        return p + "loop";
    }
}
