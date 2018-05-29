package commands.music_handling;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
import net.dv8tion.jda.core.entities.*;

import java.util.LinkedList;

public class GuildPlayer extends AudioEventAdapter {

    private LinkedList<QueueItem> queue = new LinkedList<>();
    private Guild guild;
    private AudioPlayer player;
    private boolean loopMode = false;

    public GuildPlayer(Guild guild) {
        this.guild = guild;
        this.player = GuildPlayerManager.getAudioPlayerManager().createPlayer();

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

    public void switchLoopMode() {
        this.loopMode = !this.loopMode;
    }

    private AudioTrack getNextTrack() {
        while (!queue.isEmpty()) {
            if (queue.peekFirst().isEmpty()) {
                queue.poll();
            } else {
                return queue.peekFirst().pollTrack();
            }
        }
        return null;
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
        AudioTrack nextTrack;
        if (loopMode) nextTrack = lastTrack.makeClone();
        else nextTrack = getNextTrack();
        if (nextTrack != null) this.player.playTrack(nextTrack);
        else {
            new Thread(() -> {
                this.guild.getAudioManager().closeAudioConnection();
            }).start();
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
}
