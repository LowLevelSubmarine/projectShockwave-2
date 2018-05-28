package messages;

import commands.handling.CommandHandler;
import commands.handling.SecurityLevel;
import core.JDAHandler;
import core.Statics;
import fr.bmartel.protocol.http.utils.ExceptionUtils;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;

import java.awt.*;
import java.util.Map;

public class MsgBuilder {

    private EmbedBuilder builder;

    private MsgBuilder(Color color, String emote, String title) {
        base(color, emote + " - " + title.toUpperCase());
    }
    private MsgBuilder(Color color, String emote) {
        base(color, emote);
    }

    private MsgBuilder setDescription(String description) {
        builder.setDescription(description);
        return this;
    }
    private MsgBuilder addField(String name, String value, boolean inline) {
        builder.addField(name, value, inline);
        return this;
    }
    private MessageEmbed build() {
        return builder.build();
    }

    private void base(Color color, String headline) {
        this.builder = new EmbedBuilder();
        this.builder.setColor(color);
        this.builder.setTitle(headline);
    }







    private static final Color PSW2COLOR = new Color(255, 101, 0);
    private static String link(String text, String link) {
        return "[" + text + "](" + link + ")";
    }
    private static String selfMention() {
        return JDAHandler.getJDA().getSelfUser().getAsMention();
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
    public static MessageEmbed exceptionLogInfo(Exception exception) {
        MsgBuilder builder = new MsgBuilder(PSW2COLOR, "\uD83D\uDED1", "EXCEPTION");
        builder.setDescription("Ich hatte einen Fehler!");
        builder.addField("Errorcode", ExceptionUtils.getExceptionMessage(exception), false);
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
    public static MessageEmbed commandDescription(String invoke, String description, String syntax) {
        MsgBuilder builder = new MsgBuilder(PSW2COLOR, "ℹ" ,"COMMAND " + invoke.toUpperCase());
        builder.addField("Beschreibung", description, false);
        builder.addField("Syntax", syntax, false);
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
            builder.addField(categoryName, commandList.get(categoryName), true);
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
    public static MessageEmbed info(String hosterMention, String devMention, String serverInvite) {
        MsgBuilder builder = new MsgBuilder(PSW2COLOR, "ℹ", "INFORMATION");
        builder.setDescription("Dieser Bot wurde ursprünglich für den " + link("GamingNation", serverInvite) + " Server entwickelt.");
        builder.addField("Hoster", hosterMention, true);
        builder.addField("Entwickler", devMention, true);
        return builder.build();
    }
    public static MessageEmbed version(String versionNumber, String versionTitle) {
        MsgBuilder builder = new MsgBuilder(PSW2COLOR, "ℹ", "VERSION");
        builder.setDescription(selfMention() + " läuft aktuell mit folgender " + Statics.TITLE + " Version.");
        builder.addField("Nummer", versionNumber, true);
        builder.addField("Name", versionTitle, true);
        return builder.build();
    }
    public static MessageEmbed invite(String botInvite) {
        MsgBuilder builder = new MsgBuilder(PSW2COLOR, "\uD83D\uDD17", "SHARE");
        builder.setDescription("Mit diesem " + link("Link", botInvite) + " kannst du " + selfMention() + " auch auf deinen Server einladen.");
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
        builder.setDescription("Folgendes hat sich seit der letzten Version von \"" + Statics.TITLE + "\" geändert:");
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
        MsgBuilder builder = new MsgBuilder(PSW2COLOR, "", "ADDED SNAPPY");
        builder.setDescription("Dem Snappy " + key + " wurde der Wert " + value + " zugewiesen");
        return builder.build();
    }
    public static MessageEmbed removedSnappy(String key) {
        MsgBuilder builder = new MsgBuilder(PSW2COLOR, "", "REMOVED SNAPPY");
        builder.setDescription("Der Snappy " + key + " wurde erfolgreich entfernt");
        return builder.build();
    }
    public static MessageEmbed setVolume(int volume) {
        MsgBuilder builder = new MsgBuilder(PSW2COLOR, "", "SET VOLUME");
        builder.setDescription("Die Lautstärke wurde erfolgreich auf " + volume + "% gesteltt");
        return builder.build();
    }
}
