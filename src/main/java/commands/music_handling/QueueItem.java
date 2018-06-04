package commands.music_handling;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import commands.handling.ButtonEvent;
import commands.handling.ButtonHandler;
import commands.handling.ButtonHook;
import core.JDAHandler;
import core.Statics;
import data.DATA;
import messages.MsgBuilder;
import net.dv8tion.jda.core.entities.*;
import tools.Toolkit;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

public class QueueItem implements ButtonHook {

    private LinkedList<AudioTrack> tracks;
    private String lyricsSource;
    private ArrayList<MessageEmbed> lyricsPages = new ArrayList<>();
    private int index = 0;
    private Member member;
    private TextChannel channel;
    private Message message;
    private String playlistTitle;
    private String playlistUrl;

    public QueueItem(AudioTrack track, Member member) {
        this.tracks = new LinkedList<>();
        this.tracks.add(track);
        this.member = member;
        this.channel = DATA.guild(this.member.getGuild()).getMusicChannel();
        requestLyrics(track.getInfo().title);

    }

    public QueueItem(LinkedList<AudioTrack> tracks, String playlistTitle, String playlistUrl, Member member) {
        this.tracks = tracks;
        this.member = member;
        this.channel = DATA.guild(this.member.getGuild()).getMusicChannel();
        this.playlistTitle = playlistTitle;
        this.playlistUrl = playlistUrl;
    }

    public void clear() {
        this.index = this.tracks.size() - 1;
    }

    public void finishedTrack() {
        this.index = this.index + 1;
    }

    public AudioTrack getTrack() {
        if (isFinished()) return null;
        return this.tracks.get(this.index);
    }

    public AudioTrack getPlayedTrack() {
        if (this.index < 1) return null;
        return this.tracks.get(this.index - 1);
    }

    public boolean isFinished() {
        return this.tracks.size() <= this.index;
    }

    public boolean isPlaylist() {
        return this.tracks.size() > 1;
    }

    public Member getMember() {
        return this.member;
    }

    public String getPlaylistTitle() {
        return this.playlistTitle;
    }

    public String getPlaylistUrl() {
        return this.playlistUrl;
    }

    public String getTrackTitle() {
        return getTrack().getInfo().title;
    }

    public String getTrackUrl() {
        return getTrack().getInfo().uri;
    }

    public String getPlayedTrackTitle() {
        return getPlayedTrack().getInfo().title;
    }

    public String getPlayedTrackUrl() {
        return getPlayedTrack().getInfo().uri;
    }

    public void queued() {
        MessageEmbed embed = MsgBuilder.itemQueued(this);
        this.message = this.channel.sendMessage(embed).complete();
        this.message.addReaction("❌").queue();
        ButtonHandler.registerTicket(this.message, this);
    }

    public void playing() {
        MessageEmbed embed = MsgBuilder.itemPlaying(this);
        this.message.editMessage(embed).complete();
        this.message.clearReactions().complete();
        this.message.addReaction("\u23F8").queue();
        this.message.addReaction("⏭").queue();
        if (isPlaylist()) this.message.addReaction("⬇").queue();
        if (!this.lyricsPages.isEmpty()) this.message.addReaction("\uD83D\uDCC4").queue();
    }

    public void paused() {
        MessageEmbed embed = MsgBuilder.itemPaused(this);
        this.message.editMessage(embed).complete();
        this.message.clearReactions().complete();
        this.message.addReaction("▶").queue();
        this.message.addReaction("⏭").queue();
        if (isPlaylist()) this.message.addReaction("⬇").queue();
        if (!this.lyricsPages.isEmpty()) this.message.addReaction("\uD83D\uDCC4").queue();
    }

    public void dequeued() {
        ButtonHandler.revokeTicket(this.message);
        MessageEmbed embed = MsgBuilder.itemDequeued(this);
        this.message.editMessage(embed).complete();
        this.message.clearReactions().queue();
        this.message.delete().queueAfter(5, TimeUnit.SECONDS);
    }

    public void played() {
        ButtonHandler.revokeTicket(this.message);
        MessageEmbed embed = MsgBuilder.itemPlayed(this);
        this.message.editMessage(embed).complete();
        this.message.clearReactions().queue();
    }

    @Override
    public void onButtonPress(ButtonEvent event) {
        switch (event.getEmote()) {
            case "❌":
                GuildPlayerManager.getGuildPlayer(this.channel.getGuild()).dequeue(this);
                break;
            case "\u23F8":
                GuildPlayerManager.getGuildPlayer(this.channel.getGuild()).setPaused(true);
                ButtonHandler.registerTicket(event, this);
                break;
            case "▶":
                GuildPlayerManager.getGuildPlayer(this.channel.getGuild()).setPaused(false);
                ButtonHandler.registerTicket(event, this);
                break;
            case "⏭":
                GuildPlayerManager.getGuildPlayer(this.channel.getGuild()).skipTrack();
                break;
            case "⬇":
                GuildPlayerManager.getGuildPlayer(this.channel.getGuild()).skipPlaylist();
                break;
            case "\uD83D\uDCC4":
                if (!this.lyricsPages.isEmpty()) sendLyrics(event.getUser());
                break;
            default:
                ButtonHandler.registerTicket(event, this);
                break;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof QueueItem)) return false;
        QueueItem a = (QueueItem) obj;
        if (a.message == null || this.message == null) return false;
        return a.message.equals(this.message);
    }

    @Override
    public int hashCode() {
        return this.message.hashCode();
    }

    private void requestLyrics(String searchParam) {
        new Thread(() -> {
            //Get possible lyric web paths of genius.com
            ArrayList<String> paths = JDAHandler.getGA().searchSongPath(searchParam, true);

            //Return if no results found
            if (paths.isEmpty()) return;

            //BuildPath
            String path = paths.get(0);

            //Get raw lyrics of first path
            String foundLyrics = JDAHandler.getGA().getLyrics(path);

            //Return if lyrics does not have the right size
            if (foundLyrics.length() < Statics.MINLYRICSLENGtH) return;

            //Render the lyrics and add button to message if the song is the first in the queue
            renderLyrics(foundLyrics, "https://genius.com" + path);
            if (GuildPlayerManager.getGuildPlayer(this.member.getGuild()).isPlaying(this)) {
                this.message.addReaction("\uD83D\uDCC4").queue();
            }
        }).start();
    }

    private void renderLyrics(String lyrics, String lyricsSource) {
        ArrayList<MessageEmbed> embeds = new ArrayList<>();
        ArrayList<String> pagesStrings = splitLyrics(lyrics);
        int pages = pagesStrings.size();
        if (pages == 1) {
            MessageEmbed embed = MsgBuilder.lyrics(pagesStrings.get(0), lyricsSource, null, null);
            embeds.add(embed);
        } else {
            for (int page = 1; page <= pages; page++) {
                MessageEmbed embed = MsgBuilder.lyrics(pagesStrings.get(page - 1), lyricsSource, page, pages);
                embeds.add(embed);
            }
        }
        this.lyricsPages = embeds;
    }

    private static ArrayList<String> splitLyrics(String lyrics) {
        return Toolkit.getMaxStringsSplittedAt(lyrics, Statics.MAXLYRICSPAGESLENGTH, "\n\n");
    }

    private void sendLyrics(User user) {
        PrivateChannel channel = user.openPrivateChannel().complete();
        for (MessageEmbed embed : this.lyricsPages) {
            channel.sendMessage(embed).queue();
        }
    }
}
