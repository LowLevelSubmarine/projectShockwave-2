package commands.music_handling;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import data.DATA;
import net.dv8tion.jda.core.entities.Guild;
import tools.Toolkit;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class GuildPlayerManager {

    private static final HashMap<Guild, GuildPlayer> GUILDPLAYERS = new HashMap<>();
    private static final AudioPlayerManager MANAGER = createAudioPlayerManager();

    public static GuildPlayer get(Guild guild) {
        if (GUILDPLAYERS.containsKey(guild)) {
            return GUILDPLAYERS.get(guild);
        } else {
            return create(guild);
        }
    }

    public static List<GuildPlayer> getAllPlaying() {
        List<GuildPlayer> players = new LinkedList<>();
        for (GuildPlayer player : GUILDPLAYERS.values()) {
            if (player.isPlaying()) {
                players.add(player);
            }
        }
        return players;
    }

    public static List<GuildPlayer> getAll() {
        return new LinkedList<>(GUILDPLAYERS.values());
    }

    public static boolean has(Guild guild) {
        return GUILDPLAYERS.containsKey(guild);
    }

    private static GuildPlayer create(Guild guild) {
        //Create GuildPlayer object
        GuildPlayer guildPlayer = new GuildPlayer(guild);
        //Set frame buffer duration of DATA
        int frameBufferDuration = DATA.bot().getFrameBufferDuration();
        guildPlayer.setFrameBufferDuration(frameBufferDuration);
        //Add the GuildPlayer to the HashMap
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
