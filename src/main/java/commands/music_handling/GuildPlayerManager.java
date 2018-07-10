package commands.music_handling;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import net.dv8tion.jda.core.entities.Guild;
import tools.Toolkit;

import java.util.HashMap;

public class GuildPlayerManager {

    private static final HashMap<Guild, GuildPlayer> GUILDPLAYERS = new HashMap<>();
    private static final AudioPlayerManager MANAGER = createAudioPlayerManager();

    public static GuildPlayer getGuildPlayer(Guild guild) {
        if (GUILDPLAYERS.containsKey(guild)) {
            return GUILDPLAYERS.get(guild);
        } else {
            return createGuildPlayer(guild);
        }
    }

    public static boolean hasGuildPlayer(Guild guild) {
        return GUILDPLAYERS.containsKey(guild);
    }

    private static GuildPlayer createGuildPlayer(Guild guild) {
        GuildPlayer guildPlayer = new GuildPlayer(guild);
        GUILDPLAYERS.put(guild, guildPlayer);
        return guildPlayer;
    }

    public static void searchTracks(String identifier, TrackSearchResultHook hook) {
        if (!Toolkit.startsWith(identifier, "http://", "https://")) {
            identifier = "ytsearch:" + identifier;
        }
        MANAGER.loadItem(identifier, new TrackSearchResultHandler(hook, identifier));
    }

    static AudioPlayerManager getAudioPlayerManager() {
        return MANAGER;
    }

    private static AudioPlayerManager createAudioPlayerManager() {
        AudioPlayerManager audioPlayerManager = new DefaultAudioPlayerManager();
        AudioSourceManagers.registerRemoteSources(audioPlayerManager);
        return audioPlayerManager;
    }
}
