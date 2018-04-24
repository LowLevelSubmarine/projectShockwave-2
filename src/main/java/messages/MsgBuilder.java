package messages;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;

import java.awt.*;

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

    public static MessageEmbed bootupNotification() {
        MsgBuilder builder = new MsgBuilder(PSW2COLOR, "\uD83D\uDD3A", "BOOTED UP");
        builder.setDescription("ProjectShockwave ist nun Betriebsbereit");
        return builder.build();
    }
    public static MessageEmbed restartNotification(String reason, int seconds) {
        MsgBuilder builder = new MsgBuilder(PSW2COLOR, "\uD83D\uDD3B", "SHUTTING DOWN");
        builder.setDescription("ProjectShockwave wird in " + seconds + " Sekunden neu gestartet.");
        if (reason != null) builder.addField("Grund", reason, false);
        return builder.build();
    }
    public static MessageEmbed shutdownNotification(String reason, int seconds) {
        MsgBuilder builder = new MsgBuilder(PSW2COLOR, "\uD83D\uDD3B", "SHUTTING DOWN");
        builder.setDescription("ProjectShockwave wird in " + seconds + " Sekunden heruntergefahren.");
        if (reason != null) builder.addField("Grund", reason, false);
        return builder.build();
    }
}
