package commands.music;

import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import commands.handling.CommandInfo;
import commands.handling.CommandInterface;
import commands.handling.CommandType;
import commands.handling.SecurityLevel;
import commands.music_handling.*;
import net.dv8tion.jda.core.entities.Member;
import tools.Toolkit;

public class Play implements CommandInterface, TrackSearchResultHook {

    Member member;

    @Override
    public String invoke() {
        return "play";
    }

    @Override
    public CommandType type() {
        return CommandType.MUSIC;
    }

    @Override
    public SecurityLevel securityLevel() {
        return SecurityLevel.NONE; //TODO: Add DJ Level
    }

    @Override
    public void run(CommandInfo info) {
        //Get all the values
        String identifier = info.getRaw(1);

        //Report wrong syntax
        if (identifier == null) {
            info.wrongSyntax();
            return;
        }

        //Command stuff
        this.member = info.getMember();
        identifier = makeSearchstring(identifier);
        GuildPlayerManager.searchTracks(identifier, this);
    }

    @Override
    public String title() {
        return "Spielt Musik ab";
    }

    @Override
    public String description() {
        return "Spielt nach Einen YouTube Link, eine YouTube Playlist oder das erste Ergebnis einer YouTube Suche ab";
    }

    @Override
    public String syntax(String p) {
        return p + "play < Link | Suchparameter >";
    }

    @Override
    public void onTracksFound(TrackSearchResultContainer tracks) {
        GuildPlayerManager.getGuildPlayer(this.member.getGuild()).queue(new QueueItem(tracks.getFirstResult(), this.member));
    }

    @Override
    public void onTrackSearchException(FriendlyException e) {

    }

    private static String makeSearchstring(String identifier) {
        if (!Toolkit.startsWith(identifier, "http://", "https://")) {
            return "ytsearch:" + identifier;
        } else {
            return identifier;
        }
    }
}
