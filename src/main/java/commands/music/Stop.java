package commands.music;

import commands.handling.CommandInfo;
import commands.handling.CommandInterface;
import commands.handling.CommandType;
import commands.handling.SecurityLevel;
import commands.music_handling.GuildPlayerManager;

public class Stop implements CommandInterface {
    @Override
    public String invoke() {
        return "stop";
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
            GuildPlayerManager.get(info.getGuild()).stop();
        }
    }

    @Override
    public String title() {
        return "Stoppt die aktuelle Wiedergabe";
    }

    @Override
    public String description() {
        return "Stoppt die aktuelle Wiedergabe, und lässt den Bot den Voicechannel verlassen";
    }

    @Override
    public String syntax(String p) {
        return p + "stop";
    }
}
