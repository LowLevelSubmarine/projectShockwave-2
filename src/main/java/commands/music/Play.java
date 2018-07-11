package commands.music;

import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import commands.handling.CommandInfo;
import commands.handling.CommandInterface;
import commands.handling.CommandType;
import commands.handling.SecurityLevel;
import commands.music_handling.*;
import messages.MsgBuilder;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.TextChannel;

public class Play implements CommandInterface, TrackSearchResultHook {

    Member member;
    TextChannel channel;

    @Override
    public String invoke() {
        return "play";
    }

    @Override
    public boolean silent() {
        return true;
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
        this.channel = info.getChannel();
        this.member = info.getMember();
        //Check for snappys
        identifier = SnappyConverter.convert(identifier, this.member);
        //Search by the identifier
        GuildPlayerManager.searchTracks(identifier, this);
    }

    @Override
    public String title() {
        return "Spielt Musik ab";
    }

    @Override
    public String description() {
        return "Spielt einen YouTube Link, eine Playlist oder das erste Ergebnis einer Suche ab.";
    }

    @Override
    public String syntax(String p) {
        return p + "play < Link | Suchparameter >";
    }

    @Override
    public void onTracksFound(TrackSearchResultContainer results) {
        GuildPlayer gPlayer = GuildPlayerManager.get(this.member.getGuild());
        if (results.isPlaylist()) {
            gPlayer.queue(results.getTracksAsPlaylist(), results.getPlaylistTitle(), results.getPlaylistUrl(), this.member);
        } else if (results.isUrlResult()) {
            gPlayer.queue(results.getUrlResult(), this.member);
        } else if (results.areSearchresults()) {
            gPlayer.queue(results.getSearchResult(0), this.member);
        }
    }

    @Override
    public void onNoMatches() {
        MessageEmbed embed = MsgBuilder.notTrackFound();
        this.channel.sendMessage(embed).queue();
    }

    @Override
    public void onTrackSearchException(FriendlyException e) {
        onNoMatches();
    }
}
