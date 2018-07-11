package commands.music_handling;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
import core.VolumeConverter;
import data.DATA;
import net.dv8tion.jda.core.entities.*;

import java.util.LinkedList;
import java.util.List;
import java.util.TimerTask;

public class GuildPlayer extends AudioEventAdapter {

    private LinkedList<QueueItem> queue = new LinkedList<>();
    private Guild guild;
    private AudioPlayer player;

    public GuildPlayer(Guild guild) {
        this.guild = guild;
        this.player = GuildPlayerManager.getAudioPlayerManager().createPlayer();

        this.player.addListener(this);
        this.setVolume(DATA.guild(this.guild).getVolume());
        this.guild.getAudioManager().setSendingHandler(new PlayerSendHandler(this.player));
    }

    public void queue(AudioTrack track, Member member) {
        QueueItem item = new QueueItem(track, member);
        item.queued();
        this.queue.add(item);
        if (!isPlaying()) this.player.playTrack(getTrack());
    }

    public void queue(LinkedList<AudioTrack> tracks, String playlistTitle, String playlistLink, Member member) {
        QueueItem item = new QueueItem(tracks, playlistTitle, playlistLink, member);
        item.queued();
        this.queue.add(item);
        if (!isPlaying()) this.player.playTrack(getTrack());
    }

    public void dequeue(QueueItem item) {
        this.queue.remove(item);
        item.dequeued();
    }

    public void skipTrack() {
        this.player.stopTrack();
    }

    public void skipPlaylist() {
        this.queue.peekFirst().clear();
        this.player.stopTrack();
    }

    public void stop() {
        //Deny execution if the player is not playing
        if (!isPlaying()) {
            return;
        }
        LinkedList<QueueItem> exceptFirst = new LinkedList<>(this.queue);
        exceptFirst.removeFirst();
        for (QueueItem item : exceptFirst) {
            dequeue(item);
        }
        skipPlaylist(); //...or single track
    }

    public Guild getGuild() {
        return this.guild;
    }

    public boolean isPlaying() {
        return this.player.getPlayingTrack() != null;
    }

    public boolean isPlaying(QueueItem item) {
        return this.queue.peekFirst().equals(item);
    }

    public void setVolume(int volume) {
        volume = VolumeConverter.toIntern(volume);
        this.player.setVolume(volume);
    }

    public void setPaused(boolean pause) {
        this.player.setPaused(pause);
        if (pause) this.queue.peekFirst().paused();
        else this.queue.peekFirst().playing();
    }

    private AudioTrack getTrack() {
        return this.queue.peekFirst().getTrack();
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
        this.queue.peekFirst().playing();
        if (!this.guild.getAudioManager().isConnected()) {
            GuildVoiceState voiceState = this.queue.peekFirst().getMember().getVoiceState();
            if (voiceState.inVoiceChannel()) this.guild.getAudioManager().openAudioConnection(voiceState.getChannel());
        }
    }

    /**
     * @param player Audio player
     * @param lastTrack Audio track that ended
     * @param endReason The reason why the track stopped playing
     */
    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack lastTrack, AudioTrackEndReason endReason) {
        this.queue.peekFirst().finishedTrack();
        if (this.queue.peekFirst().isFinished()) {
            this.queue.peekFirst().played();
            this.queue.pollFirst();
        }
        if (!this.queue.isEmpty()) {
            this.player.playTrack(this.queue.peekFirst().getTrack());
        } else {
            new Thread(() -> this.guild.getAudioManager().closeAudioConnection()).start();
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
