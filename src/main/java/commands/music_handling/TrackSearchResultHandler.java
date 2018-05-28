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
        TrackContainer tracks = new TrackContainer(track);
        hook.onTracksFound(tracks);
    }

    @Override
    public void playlistLoaded(AudioPlaylist playlist) {
        TrackContainer tracks = new TrackContainer(playlist.getTracks());
        hook.onTracksFound(tracks);
    }

    @Override
    public void noMatches() {
        TrackContainer tracks = new TrackContainer();
        hook.onTracksFound(tracks);
    }

    @Override
    public void loadFailed(FriendlyException exception) {
        hook.onTrackSearchException(exception);
    }
}
