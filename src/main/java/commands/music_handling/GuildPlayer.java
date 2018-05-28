package commands.music_handling;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
import net.dv8tion.jda.core.entities.*;

import java.util.LinkedList;

public class GuildPlayer extends AudioEventAdapter {

    private static final AudioPlayerManager MANAGER = createAudioPlayerManager();

    private LinkedList<QueueItem> queue = new LinkedList<>();
    private Guild guild;
    private AudioPlayer player;
    private VoiceChannel joinChannel;
    private boolean loopMode = false;

    public GuildPlayer(Guild guild) {
        this.guild = guild;
        this.player = MANAGER.createPlayer();

        this.player.addListener(this);
        this.player.setVolume(100);
        this.guild.getAudioManager().setSendingHandler(new PlayerSendHandler(this.player));
    }

    public void queue(QueueItem item) {
        this.queue.add(item);
        if (!isPlaying()) {
            this.player.playTrack(getNextTrack());
        }
    }

    public Guild getGuild() {
        return this.guild;
    }

    public boolean isPlaying() {
        return this.player.getPlayingTrack() != null;
    }

    public void setVolume(int volume) {
        this.player.setVolume(volume);
    }

    public void setPaused(boolean pause) {
        this.player.setPaused(pause);
    }

    private AudioTrack getNextTrack() {
        AudioTrack nextTrack = null;
        for (boolean loop = true; loop;) {
            if (queue.peekFirst().isEmpty()) {
                queue.poll();
            } else {
                if (loopMode) nextTrack = queue.peekFirst().peekTrack();
                else nextTrack = queue.peekFirst().pollTrack();
                loop = false;
            }
        }
        return nextTrack;
    }

    /**
     * @param player Audio player
     */
    @Override
    public void onPlayerPause(AudioPlayer player) {
        // Adapter dummy method
    }

    /**
     * @param player Audio player
     */
    @Override
    public void onPlayerResume(AudioPlayer player) {
        // Adapter dummy method
    }

    /**
     * @param player Audio player
     * @param track Audio track that started
     */
    @Override
    public void onTrackStart(AudioPlayer player, AudioTrack track) {
        if (!this.guild.getAudioManager().isConnected()) {
            Member member = track.getUserData(Member.class);
            GuildVoiceState voiceState = member.getVoiceState();
            if (voiceState.inVoiceChannel()) {
                this.guild.getAudioManager().openAudioConnection(voiceState.getChannel());
            }
        }
    }

    /**
     * @param player Audio player
     * @param lastTrack Audio track that ended
     * @param endReason The reason why the track stopped playing
     */
    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack lastTrack, AudioTrackEndReason endReason) {
        AudioTrack nextTrack = getNextTrack();
        if (nextTrack != null) {
            this.player.playTrack(nextTrack);
        } else {
            this.guild.getAudioManager().closeAudioConnection();
        }
    }

    /**
     * @param player Audio player
     * @param track Audio track where the exception occurred
     * @param exception The exception that occurred
     */
    @Override
    public void onTrackException(AudioPlayer player, AudioTrack track, FriendlyException exception) {
        // Adapter dummy method
    }

    /**
     * @param player Audio player
     * @param track Audio track where the exception occurred
     * @param thresholdMs The wait threshold that was exceeded for this event to trigger
     */
    @Override
    public void onTrackStuck(AudioPlayer player, AudioTrack track, long thresholdMs) {
        // Adapter dummy method
    }




    public static void searchTracks(String identifier, TrackSearchResultHook hook) {
        MANAGER.loadItem(identifier, new TrackSearchResultHandler(hook));
    }

    private static AudioPlayerManager createAudioPlayerManager() {
        AudioPlayerManager audioPlayerManager = new DefaultAudioPlayerManager();
        AudioSourceManagers.registerRemoteSources(audioPlayerManager);
        return audioPlayerManager;
    }
}
