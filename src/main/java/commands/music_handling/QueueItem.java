package commands.music_handling;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import commands.handling.ButtonEvent;
import commands.handling.ButtonHandler;
import commands.handling.ButtonHook;
import data.DATA;
import messages.MsgBuilder;
import net.dv8tion.jda.core.entities.*;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

public class QueueItem implements ButtonHook {

    private LinkedList<AudioTrack> tracks;
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
    }

    public void paused() {
        MessageEmbed embed = MsgBuilder.itemPaused(this);
        this.message.editMessage(embed).complete();
        this.message.clearReactions().complete();
        this.message.addReaction("▶").queue();
        this.message.addReaction("⏭").queue();
        if (isPlaylist()) this.message.addReaction("⬇").queue();
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
                event.reregister();
                break;
            case "▶":
                GuildPlayerManager.getGuildPlayer(this.channel.getGuild()).setPaused(false);
                event.reregister();
                break;
            case "⏭":
                GuildPlayerManager.getGuildPlayer(this.channel.getGuild()).skipTrack();
                break;
            case "⬇":
                GuildPlayerManager.getGuildPlayer(this.channel.getGuild()).skipPlaylist();
                break;
            default:
                event.reregister();
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
}
