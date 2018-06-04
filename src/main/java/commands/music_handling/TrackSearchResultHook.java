package commands.music_handling;

import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;

public interface TrackSearchResultHook {
    void onTracksFound(TrackSearchResultContainer tracks);
    void onNoMatches();
    void onTrackSearchException(FriendlyException e);
}
