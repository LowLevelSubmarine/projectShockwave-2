package commands.music_handling;

import net.dv8tion.jda.core.entities.Guild;

import java.util.HashMap;

public class PlayerHandler {

    private static final HashMap<Guild, GuildPlayer> QUEUEMANAGERS = new HashMap<>();

    public static GuildPlayer getOrCreateQueue(Guild guild) {
        if (QUEUEMANAGERS.containsKey(guild)) {
            return QUEUEMANAGERS.get(guild);
        } else {
            return createQueue(guild);
        }
    }

    public static GuildPlayer getQueue(Guild guild) {
        return QUEUEMANAGERS.getOrDefault(guild, null);
    }

    private static GuildPlayer createQueue(Guild guild) {
        GuildPlayer guildPlayer = new GuildPlayer(guild);
        QUEUEMANAGERS.put(guild, guildPlayer);
        return guildPlayer;
    }
}
