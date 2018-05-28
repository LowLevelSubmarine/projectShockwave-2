package commands.music_handling;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import java.util.LinkedList;
import java.util.List;

public class TrackContainer {

    LinkedList<AudioTrack> tracks;

    public TrackContainer(AudioTrack track) {
        this.tracks = new LinkedList<>();
        this.tracks.add(track);
    }

    public TrackContainer(List<AudioTrack> tracks) {
        this.tracks = new LinkedList<>();
        this.tracks.addAll(tracks);
    }

    public TrackContainer() {
        this.tracks = new LinkedList<>();
    }

    public AudioTrack getFirstResult() {
        return this.tracks.get(0);
    }
}
