package commands.music_handling;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import java.util.LinkedList;
import java.util.List;

public class TrackSearchResultContainer {

    LinkedList<AudioTrack> tracks;

    public TrackSearchResultContainer(AudioTrack track) {
        this.tracks = new LinkedList<>();
        this.tracks.add(track);
    }

    public TrackSearchResultContainer(List<AudioTrack> tracks) {
        this.tracks = new LinkedList<>();
        this.tracks.addAll(tracks);
    }

    public TrackSearchResultContainer() {
        this.tracks = new LinkedList<>();
    }

    public AudioTrack getFirstResult() {
        return this.tracks.get(0);
    }
}
