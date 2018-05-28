package commands.music_handling;

import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;

public interface TrackSearchResultHook {
    void onTracksFound(TrackContainer tracks);
    void onTrackSearchException(FriendlyException e);
}
