package commands.music;

import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import commands.handling.*;
import commands.music_handling.GuildPlayer;
import commands.music_handling.GuildPlayerManager;
import commands.music_handling.TrackSearchResultContainer;
import commands.music_handling.TrackSearchResultHook;
import core.Emotes;
import messages.MsgBuilder;
import net.dv8tion.jda.core.entities.*;

import java.util.LinkedList;

public class Search implements CommandInterface, TrackSearchResultHook, ButtonHook {

    LinkedList<AudioTrack> results;
    TextChannel channel;
    Guild guild;
    Member member;
    Message resultMessage;

    @Override
    public String invoke() {
        return "search";
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
        return SecurityLevel.NONE;
    }

    @Override
    public void run(CommandInfo info) {
        String identifier = info.getRaw(1);
        this.channel = info.getChannel();
        this.guild = info.getGuild();
        this.member = info.getMember();

        if (identifier == null) {
            info.wrongSyntax();
            return;
        }

        GuildPlayerManager.searchTracks(identifier, this);
    }

    @Override
    public String title() {
        return "Zeigt die ersten 5 Ergebnisse einer Musiktitelsuche";
    }

    @Override
    public String description() {
        return "Zeigt die ersten 5 Ergebnisse einer Musiktitelsuche. Danach kann eines der Ergebnisse ausgewählt werden.";
    }

    @Override
    public String syntax(String p) {
        return p + "search";
    }

    @Override
    public void onTracksFound(TrackSearchResultContainer tracks) {
        //Get the search results
        this.results = tracks.getSearchResults();
        //Make sure there are not more than 5 results
        int maxResults = results.size();
        if (maxResults > 5) {
            maxResults = 5;
        }
        this.results = new LinkedList<>(this.results.subList(0, maxResults));
        //Send message with results
        MessageEmbed embed = MsgBuilder.trackSearchResults(this.results);
        this.resultMessage = this.channel.sendMessage(embed).complete();
        //Add buttons so that the user can select one of the tracks
        this.resultMessage.addReaction(Emotes.NR1).complete();
        this.resultMessage.addReaction(Emotes.NR2).complete();
        this.resultMessage.addReaction(Emotes.NR3).complete();
        this.resultMessage.addReaction(Emotes.NR4).complete();
        this.resultMessage.addReaction(Emotes.NR5).complete();
        this.resultMessage.addReaction(Emotes.X).complete();
        //Listen for button presses
        ButtonHandler.registerTicket(this.resultMessage, this);
    }

    @Override
    public void onButtonPress(ButtonEvent event) {
        switch (event.getEmote()) {
            case Emotes.X:
                this.resultMessage.delete().queue();
                break;
            case Emotes.NR1:
                trackSelected(event, 0);
                break;
            case Emotes.NR2:
                trackSelected(event, 1);
                break;
            case Emotes.NR3:
                trackSelected(event, 2);
                break;
            case Emotes.NR4:
                trackSelected(event, 3);
                break;
            case Emotes.NR5:
                trackSelected(event, 4);
                break;
            default:
                event.reregister();
                break;
        }
    }

    private void trackSelected(ButtonEvent event, int selection) {
        if (this.results.size() <= selection) {
            event.reregister();
        } else {
            this.resultMessage.delete().queue();
            GuildPlayer player = GuildPlayerManager.getGuildPlayer(this.guild);
            player.queue(results.get(selection), this.member);
        }
    }

    @Override
    public void onNoMatches() {
         MessageEmbed embed = MsgBuilder.trackSearchNoResults();
         this.channel.sendMessage(embed).queue();
    }

    @Override
    public void onTrackSearchException(FriendlyException e) {
        onNoMatches();
    }
}
