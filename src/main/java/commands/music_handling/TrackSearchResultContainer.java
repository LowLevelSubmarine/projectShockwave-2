package commands.music_handling;

import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import java.util.Collections;
import java.util.LinkedList;

public class TrackSearchResultContainer {

    AudioTrack track;
    AudioPlaylist playlist;
    String playlistUrl;

    public TrackSearchResultContainer(AudioTrack track) {
        this.track = track;
    }

    public TrackSearchResultContainer(AudioPlaylist playlist, String playlistUrl) {
        this.playlist = playlist;
        this.playlistUrl = playlistUrl;
    }

    public boolean isPlaylist() {
        return this.playlist != null && !this.playlist.isSearchResult();
    }

    public boolean areSearchresults() {
        return this.playlist != null && this.playlist.isSearchResult();
    }

    public boolean isUrlResult() {
        return this.track != null;
    }

    public LinkedList<AudioTrack> getTracksAsPlaylist() {
        LinkedList<AudioTrack> shuffled = new LinkedList<>(this.playlist.getTracks());
        Collections.shuffle(shuffled);
        return shuffled;
    }

    public String getPlaylistTitle() {
        return this.playlist.getName();
    }

    public String getPlaylistUrl() {
        return this.playlistUrl;
    }

    public AudioTrack getSearchResult(int i) {
        return this.playlist.getTracks().get(i);
    }

    public AudioTrack getUrlResult() {
        return this.track;
    }
}
