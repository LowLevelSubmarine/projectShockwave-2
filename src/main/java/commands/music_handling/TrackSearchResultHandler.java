package commands.music_handling;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

public class TrackSearchResultHandler implements AudioLoadResultHandler {

    private TrackSearchResultHook hook;

    public TrackSearchResultHandler(TrackSearchResultHook hook) {
        this.hook = hook;
    }

    @Override
    public void trackLoaded(AudioTrack track) {
        TrackSearchResultContainer tracks = new TrackSearchResultContainer(track);
        hook.onTracksFound(tracks);
    }

    @Override
    public void playlistLoaded(AudioPlaylist playlist) {
        TrackSearchResultContainer tracks = new TrackSearchResultContainer(playlist.getTracks());
        hook.onTracksFound(tracks);
    }

    @Override
    public void noMatches() {
        TrackSearchResultContainer tracks = new TrackSearchResultContainer();
        hook.onTracksFound(tracks);
    }

    @Override
    public void loadFailed(FriendlyException exception) {
        hook.onTrackSearchException(exception);
    }
}
