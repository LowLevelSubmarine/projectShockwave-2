package commands.music_handling;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.core.entities.Member;

import java.util.LinkedList;

public class QueueItem {
    private LinkedList<AudioTrack> tracks;
    private String playlistName;

    public QueueItem(AudioTrack track, Member requester) {
        track.setUserData(requester);
        this.tracks = new LinkedList<>();
        this.tracks.add(track);
    }

    public QueueItem(LinkedList<AudioTrack> tracks, Member requester, String playlistName) {
        for (AudioTrack track : tracks) {
            track.setUserData(requester);
        }
        this.tracks = tracks;
        this.playlistName = playlistName;
    }

    public void clear() {
        this.tracks.clear();
    }

    public AudioTrack nextTrack() {
        this.tracks.pollFirst();
        return this.tracks.peekFirst();
    }

    public AudioTrack pollTrack() {
        return this.tracks.pollFirst();
    }

    public AudioTrack peekTrack() {
        return this.tracks.peekFirst();
    }

    public boolean isEmpty() {
        return this.tracks.isEmpty();
    }
}
