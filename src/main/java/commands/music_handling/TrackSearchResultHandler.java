package commands.music_handling;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

public class TrackSearchResultHandler implements AudioLoadResultHandler {

    private TrackSearchResultHook hook;
    private String identifier;

    public TrackSearchResultHandler(TrackSearchResultHook hook, String identifier) {
        this.hook = hook;
        this.identifier = identifier;
    }

    @Override
    public void trackLoaded(AudioTrack track) {
        TrackSearchResultContainer results = new TrackSearchResultContainer(track);
        hook.onTracksFound(results);
    }

    @Override
    public void playlistLoaded(AudioPlaylist playlist) {
        TrackSearchResultContainer results = new TrackSearchResultContainer(playlist, this.identifier);
        hook.onTracksFound(results);
    }

    @Override
    public void noMatches() {
        hook.onNoMatches();
    }

    @Override
    public void loadFailed(FriendlyException exception) {
        hook.onTrackSearchException(exception);
    }
}
