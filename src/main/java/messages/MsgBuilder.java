package messages;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import commands.handling.CommandHandler;
import commands.handling.SecurityLevel;
import commands.music_handling.QueueItem;
import core.*;
import data.DATA;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import statics.Emote;
import statics.InviteGenerator;
import statics.Statics;
import statics.VersionInfo;

import java.awt.*;
import java.util.LinkedList;
import java.util.Map;

public class MsgBuilder {

    private EmbedBuilder builder;

    private MsgBuilder(Color color, String emote, String title) {
        this.base(color);
        this.builder.setTitle(emote + " - " + title.toUpperCase());
    }
    private MsgBuilder(Color color, String emote) {
        this.base(color);
        this.builder.setTitle(emote);
    }
    private MsgBuilder(Color color) {
        this.base(color);
    }

    private MsgBuilder setDescription(String description) {
        this.builder.setDescription(description);
        return this;
    }
    private MsgBuilder setFooter(String text) {
        this.builder.setFooter(text, null);
        return this;
    }
    private MsgBuilder addField(String name, String value, boolean inline) {
        this.builder.addField(name, value, inline);
        return this;
    }
    private MessageEmbed build() {
        return this.builder.build();
    }

    private void base(Color color) {
        this.builder = new EmbedBuilder();
        this.builder.setColor(color);
    }



    private static final Color PSW2COLOR = new Color(255, 101, 0);
    private static String link(String text, String link) {
        return "[" + text + "](" + link + ")";
    }
    private static String selfMention() {
        return BotHandler.getJDA().getSelfUser().getAsMention();
    }

    public static MessageEmbed info() {
        User developer = BotHandler.getJDA().getUserById(Statics.DEVELOPERUSERID);
        User hoster = BotHandler.getJDA().getUserById(DATA.config().getHoster());
        MsgBuilder builder = new MsgBuilder(PSW2COLOR, "ℹ", "BOT INFORMATION");
        builder.setDescription(selfMention() + " läuft aktuell mit " + link(VersionInfo.PROJECTTITLE, Statics.GITHUBPROJECT) +
                " welcher ein, auf Benutzerinteraktion spezialisierter, deutschsprachiger Musik-Bot ist. " +
                "Er wurde ursprünglich für den " + link("GamingNation Server", Statics.GAMINGNATIONINVITE) +
                " entwickelt, kann aber nun unabhängig genutzt werden.");
        builder.addField("Entwickler", developer.getAsMention(), true);
        builder.addField("Hoster", hoster.getAsMention(), true);
        builder.addField("Version", VersionInfo.NAME, true);
        return builder.build();
    }
    public static MessageEmbed bootupNotification() {
        MsgBuilder builder = new MsgBuilder(PSW2COLOR, "\uD83D\uDD3A", "BOOTED UP");
        builder.setDescription(selfMention() + " ist nun Betriebsbereit.");
        return builder.build();
    }
    public static MessageEmbed restartNotification(String reason, int seconds) {
        MsgBuilder builder = new MsgBuilder(PSW2COLOR, "\uD83D\uDD3B", "SHUTTING DOWN");
        builder.setDescription(selfMention() + " wird in " + seconds + " Sekunden neu gestartet!");
        if (reason != null) builder.addField("Grund", reason, false);
        return builder.build();
    }
    public static MessageEmbed shutdownNotification(String reason, int seconds) {
        MsgBuilder builder = new MsgBuilder(PSW2COLOR, "\uD83D\uDD3B", "SHUTTING DOWN");
        builder.setDescription(selfMention() + " wird in " + seconds + " Sekunden heruntergefahren!");
        if (reason != null) builder.addField("Grund", reason, false);
        return builder.build();
    }
    public static MessageEmbed missingAuthorization() {
        MsgBuilder builder = new MsgBuilder(PSW2COLOR, "⚠", "MISSING PERMISSION");
        builder.setDescription("Du hast nicht die benötigten Berechtigungen um diesen Befehl auszuführen!");
        return builder.build();
    }
    public static MessageEmbed exceptionLogInfo(String exception) {
        MsgBuilder builder = new MsgBuilder(PSW2COLOR, "\uD83D\uDED1", "EXCEPTION");
        builder.setDescription("Ich hatte einen Fehler!");
        builder.addField("Errorcode", exception, false);
        return builder.build();
    }
    public static MessageEmbed shutdownQuerry(String reason) {
        MsgBuilder builder = new MsgBuilder(PSW2COLOR, "♦", "SHUTDOWN");
        builder.setDescription("Willst du " + selfMention() + " wirklich herunterfahren?");
        if (reason != null) builder.addField("Grund", reason,false);
        return builder.build();
    }
    public static MessageEmbed restartQuerry(String reason) {
        MsgBuilder builder = new MsgBuilder(PSW2COLOR, "\uD83D\uDD04", "RESTART");
        builder.setDescription("Willst du " + selfMention() + " wirklich neustarten?");
        if (reason != null) builder.addField("Grund", reason, false);
        return builder.build();
    }
    public static MessageEmbed commandDescription(String invoke, String description, String syntax, SecurityLevel secLevel) {
        MsgBuilder builder = new MsgBuilder(PSW2COLOR, "ℹ" ,"COMMAND " + invoke.toUpperCase());
        builder.addField("Beschreibung", description, false);
        builder.addField("Syntax", syntax, true);
        builder.addField("Berechtigung", secLevel.toString(), true);
        return builder.build();
    }
    public static MessageEmbed commandDescriptionMissingCommand() {
        MsgBuilder builder = new MsgBuilder(PSW2COLOR, "ℹ", "HELP");
        builder.setDescription("Der angegebene Command existiert nicht!");
        return builder.build();
    }
    public static MessageEmbed commandList() {
        MsgBuilder builder = new MsgBuilder(PSW2COLOR, "ℹ", "COMMANDS");
        Map<String, String> commandList = CommandHandler.getCommandList();
        for (String categoryName : commandList.keySet()) {
            builder.addField(categoryName, commandList.get(categoryName), false);
        }
        return builder.build();
    }
    public static MessageEmbed speedtestProgress(String downloadProgress, String uploadProgress) {
        MsgBuilder builder = new MsgBuilder(PSW2COLOR, "⚙", "SPEEDTEST");
        if (downloadProgress == null) downloadProgress = "Wird getestet...";
        if (uploadProgress == null) uploadProgress = "Wird getestet...";
        builder.addField("Download", downloadProgress, true);
        builder.addField("Upload", uploadProgress, true);
        return builder.build();
    }
    public static MessageEmbed ping(long ping) {
        MsgBuilder builder = new MsgBuilder(PSW2COLOR, "⚙", "PING");
        builder.setDescription("Die Discord API hat aktuell einen Ping von " + ping + "ms.");
        return builder.build();
    }
    public static MessageEmbed version(String versionNumber, String versionTitle) {
        MsgBuilder builder = new MsgBuilder(PSW2COLOR, "ℹ", "VERSION");
        builder.setDescription(selfMention() + " läuft aktuell auf folgender " + VersionInfo.PROJECTTITLE + " Version:");
        builder.addField("Nummer", versionNumber, true);
        builder.addField("Name", versionTitle, true);
        return builder.build();
    }
    public static MessageEmbed invite(String botInvite) {
        MsgBuilder builder = new MsgBuilder(PSW2COLOR, "\uD83D\uDD17", "INVITE");
        builder.setDescription("Mit diesem " + link("Link", botInvite) + " kannst du " + selfMention() + " auch auf deinen Server einladen.\n" +
                "Außerdem wäre es schön wenn du mal auf dem " + link("GamingNation", Statics.GAMINGNATIONINVITE) + " Server vorbeischaust.");
        return builder.build();
    }
    public static MessageEmbed permissions() {
        MsgBuilder builder = new MsgBuilder(PSW2COLOR, "ℹ", "PERMISSIONS");
        builder.setDescription("Hier siehst du eine Übersicht aller Berechtigungen:");
        builder.addField(SecurityLevel.NONE.toString(), "Wie der Name schon sagt wird für diese Berechtigung nichts benötigt, " +
                "sie ist für Commands die nur den jeweiligen User etwas betreffen.", true);
        builder.addField(SecurityLevel.GUILD.toString(), "Diese Berechtigung erfordert den Besitz einer Rolle mit Admin Berechtigungen " +
                "auf dem ausführenden Server, sie ist für Commands mit serverweiten Auswirkungen.", true);
        builder.addField(SecurityLevel.BOT.toString(), "Diese Berechtigung kann nur von Usern mit Owner Berechtigung verteilt werden, " +
                "sie ist für hosting bezogene Commands oder für solche die alle Nutzer etwas betreffen.", true);
        builder.addField(SecurityLevel.OWNER.toString(), "Wer diese Berechtigung besitzt wird vom Bot Hoster festgelegt, " +
                "sie ist ausschließlich dafür da festzulegen wer die " + SecurityLevel.BOT.getTitle() + " Berechtigung erhält.", true);
        return builder.build();
    }
    public static MessageEmbed changelog(String changes) {
        MsgBuilder builder = new MsgBuilder(PSW2COLOR, "ℹ", "CHANGELOG");
        builder.setDescription("Folgendes hat sich seit der letzten Version von \"" + VersionInfo.PROJECTTITLE + "\" geändert:");
        builder.addField("Änderungen", changes, false);
        return builder.build();
    }
    public static MessageEmbed setPrefixDone(String oldPrefix, String newPrefix) {
        MsgBuilder builder = new MsgBuilder(PSW2COLOR, "\uD83D\uDD27", "PREFIX");
        builder.setDescription("Das Prefix wurde von \"" + oldPrefix + "\" zu \"**" + newPrefix + "**\" geändert.");
        return builder.build();
    }
    public static MessageEmbed wrongSyntaxInfo(String prefix, String invoke) {
        MsgBuilder builder = new MsgBuilder(PSW2COLOR, "⚠", "SYNTAX ERROR");
        builder.setDescription("Falscher Syntax! Schreibe \"" + prefix + "help " + invoke + "\" für mehr Hilfe.");
        return builder.build();
    }
    public static MessageEmbed addBotadminDone(User user) {
        MsgBuilder builder = new MsgBuilder(PSW2COLOR, "\uD83D\uDD27", "ADDED BOTUSER");
        builder.setDescription("Dem User " + user.getAsMention() + " wurde die " + SecurityLevel.BOT.getTitle() + " Berechtigung erteilt.");
        return builder.build();
    }
    public static MessageEmbed removeBotadminDone(User user) {
        MsgBuilder builder = new MsgBuilder(PSW2COLOR, "\uD83D\uDD27", "REMOVED BOTUSER");
        builder.setDescription("Dem User " + user.getAsMention() + " wurde die " + SecurityLevel.BOT.getTitle() + " Berechtigung entzogen.");
        return builder.build();
    }
    public static MessageEmbed setStatusDone() {
        MsgBuilder builder = new MsgBuilder(PSW2COLOR, "\uD83D\uDD27", "EDITED STATUS");
        builder.setDescription("Der Status von " + selfMention() + " wurde erfolgreich umgestellt");
        return builder.build();
    }
    public static MessageEmbed setNotifyChannelDone(TextChannel channel) {
        MsgBuilder builder = new MsgBuilder(PSW2COLOR, "\uD83D\uDD27", "SET NOTIFYCHANNEL");
        builder.setDescription("Der neue Benachrichtugungschannel ist " + channel.getAsMention() + ".");
        return builder.build();
    }
    public static MessageEmbed addedSnappy(String key, String value) {
        MsgBuilder builder = new MsgBuilder(PSW2COLOR, "\uD83D\uDD27", "ADDED SNAPPY");
        builder.setDescription("Dem Snappy " + key + " wurde der Wert " + value + " zugewiesen.");
        return builder.build();
    }
    public static MessageEmbed removedSnappy(String key) {
        MsgBuilder builder = new MsgBuilder(PSW2COLOR, "\uD83D\uDD27", "REMOVED SNAPPY");
        builder.setDescription("Der Snappy " + key + " wurde erfolgreich entfernt.");
        return builder.build();
    }
    public static MessageEmbed setVolume(int volume) {
        MsgBuilder builder = new MsgBuilder(PSW2COLOR, "\uD83D\uDD27", "SET VOLUME");
        builder.setDescription("Die Lautstärke wurde erfolgreich auf " + volume + "% gestelt.");
        return builder.build();
    }
    public static MessageEmbed setAudioBuffer(int seconds) {
        MsgBuilder builder = new MsgBuilder(PSW2COLOR, "\uD83D\uDD27", "SET AUDIO BUFFER");
        builder.setDescription("Der Audiobuffer wurde erfolgreich auf " + seconds + " Sekunden gestellt.");
        return builder.build();
    }
    public static MessageEmbed notInVoiceChannel(User user) {
        MsgBuilder builder = new MsgBuilder(PSW2COLOR, "⚠", "NOT IN VOICECHANNEL");
        builder.setDescription("Ich kann keine Musik abspielen wenn du nicht in einem Voicechannel bist.");
        return builder.build();
    }
    public static MessageEmbed notTrackFound() {
        MsgBuilder builder = new MsgBuilder(PSW2COLOR, "⚠", "NO TRACK FOUND");
        builder.setDescription("Die Suche ergab leider kein Ergebnis");
        return builder.build();
    }
    public static MessageEmbed privateMessageReminder(User user) {
        MsgBuilder builder = new MsgBuilder(PSW2COLOR, "❗", "PRIVATE MESSAGE");
        builder.setDescription(user.getAsMention() + "du hast eine Nachricht im privaten Chat erhalten.");
        return builder.build();
    }
    public static MessageEmbed itemQueued(QueueItem item) {
        MsgBuilder builder = new MsgBuilder(PSW2COLOR);
        if (item.isPlaylist()) {
            builder.setDescription("⤵ - " + link(item.getPlaylistTitle(), item.getPlaylistUrl()));
        } else {
            builder.setDescription("⤵ - " + link(item.getTrackTitle(), item.getTrackUrl()));
        }
        return builder.build();
    }
    public static MessageEmbed itemPlaying(QueueItem item) {
        MsgBuilder builder = new MsgBuilder(PSW2COLOR);
        if (item.isPlaylist()) {
            builder.setDescription("▶ - " + link(item.getPlaylistTitle(), item.getPlaylistUrl())
                    + " / " + link(item.getTrackTitle(), item.getTrackUrl()));
        } else {
            builder.setDescription("▶ - " + link(item.getTrackTitle(), item.getTrackUrl()));
        }
        return builder.build();
    }
    public static MessageEmbed itemPaused(QueueItem item) {
        MsgBuilder builder = new MsgBuilder(PSW2COLOR);
        if (item.isPlaylist()) {
            builder.setDescription("\u23F8 - " + link(item.getPlaylistTitle(), item.getPlaylistUrl())
                    + " / " + link(item.getTrackTitle(), item.getTrackUrl()));
        } else {
            builder.setDescription("\u23F8 - " + link(item.getTrackTitle(), item.getTrackUrl()));
        }
        return builder.build();
    }
    public static MessageEmbed itemDequeued(QueueItem item) {
        MsgBuilder builder = new MsgBuilder(PSW2COLOR);
        if (item.isPlaylist()) {
            builder.setDescription("❌ - " + link(item.getPlaylistTitle(), item.getPlaylistUrl()));
        } else {;
            builder.setDescription("❌ - " + link(item.getTrackTitle(), item.getTrackUrl()));
        }
        return builder.build();
    }
    public static MessageEmbed itemPlayed(QueueItem item) {
        MsgBuilder builder = new MsgBuilder(PSW2COLOR);
        if (item.isPlaylist()) {
            builder.setDescription("☑ - " + link(item.getPlaylistTitle(), item.getPlaylistUrl()));
        } else {
            builder.setDescription("☑ - " + link(item.getPlayedTrackTitle(), item.getPlayedTrackUrl()));
        }
        return builder.build();
    }
    public static MessageEmbed setMusicChannelDone(TextChannel channel) {
        MsgBuilder builder = new MsgBuilder(PSW2COLOR, "\uD83D\uDD27", "SET NOTIFYCHANNEL");
        builder.setDescription("Der neue Musikkanal ist " + channel.getAsMention() + ".");
        return builder.build();
    }
    public static MessageEmbed trackSearchResults(LinkedList<AudioTrack> results) {
        MsgBuilder builder = new MsgBuilder(PSW2COLOR, "\uD83D\uDD0E", "SEARCH RESULTS");
        String desc = Emote.NR1 + " " + link(results.get(0).getInfo().title, results.get(0).getInfo().uri) + "\n";
        if (results.size() >= 1) {
            desc += Emote.NR2 + " " + link(results.get(1).getInfo().title, results.get(0).getInfo().uri) + "\n";
        }
        if (results.size() >= 2) {
            desc += Emote.NR3 + " " + link(results.get(2).getInfo().title, results.get(0).getInfo().uri) + "\n";
        }
        if (results.size() >= 4) {
            desc += Emote.NR4 + " " + link(results.get(3).getInfo().title, results.get(0).getInfo().uri) + "\n";
        }
        if (results.size() >= 5) {
            desc += Emote.NR5 + " " + link(results.get(4).getInfo().title, results.get(0).getInfo().uri) + "\n";
        }
        builder.setDescription(desc);
        return builder.build();
    }
    public static MessageEmbed trackSearchNoResults() {
        MsgBuilder builder = new MsgBuilder(PSW2COLOR, "⚠", "NO RESULTS");
        builder.setDescription("Die Suche ergab leider keine Ergebnisse");
        return builder.build();
    }
    public static MessageEmbed noPermissions(Member member) {
        String invite = InviteGenerator.get(member.getUser());
        MsgBuilder builder = new MsgBuilder(PSW2COLOR, "❗", "MISSING PERMISSION");
        builder.setDescription("Damit " + selfMention() + " auf dem " + member.getGuild().getName() +
                " Server ohne Probleme laufen kann, werden Admin Berechtigungen " +
                "benötigt. Um Probleme zu vermeiden wird " + selfMention() + " nun den Server verlassen. " +
                "Mit diesem " + link("Invitelink", invite) + " kannst du " + selfMention() +
                " wieder zu deinem Server hinzufügen.");
        return builder.build();
    }
}
