package messages;

import commands.handling.CommandHandler;
import commands.handling.SecurityLevel;
import commands.music_handling.QueueItem;
import core.JDAHandler;
import core.Statics;
import core.VersionInfo;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;

import java.awt.*;
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
        return JDAHandler.getJDA().getSelfUser().getAsMention();
    }

    public static MessageEmbed info(String hosterMention, String devMention, String serverInvite) {
        MsgBuilder builder = new MsgBuilder(PSW2COLOR, "ℹ", "INFORMATION");
        builder.setDescription(VersionInfo.PROJECTTITLE + " läuft aktuell " + selfMention() + "  " + link("GamingNation", serverInvite) + " Server entwickelt.\n" +
                "");
        builder.addField("Hoster", hosterMention, true);
        builder.addField("Entwickler", devMention, true);
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
    public static MessageEmbed lyrics(String text, String source, Integer page, Integer pages) {
        MsgBuilder builder;
        String description = text;
        if (page == null || page == 1) {
            builder = new MsgBuilder(PSW2COLOR, "\uD83D\uDCC4", "GENIUS LYRICS");
            description = "Quelle: " + source + "\n\n" + description;
        } else {
            builder = new MsgBuilder(PSW2COLOR);
        }
        builder.setDescription(description);
        if (page != null && pages != null) builder.setFooter("Seite " + page + " von " + pages + ".");
        return builder.build();
    }
}
