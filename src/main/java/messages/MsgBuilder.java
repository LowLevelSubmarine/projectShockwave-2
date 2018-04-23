package messages;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;

import java.awt.*;

public class MsgBuilder {

    private EmbedBuilder builder;

    public MsgBuilder(Color color, String emote, String title) {
        base(color, emote + " - " + title.toUpperCase());
    }
    public MsgBuilder(Color color, String emote) {
        base(color, emote);
    }

    public MsgBuilder setDescription(String description) {
        builder.setDescription(description);
        return this;
    }
    public MsgBuilder addField(String name, String value, boolean inline) {
        builder.addField(name, value, inline);
        return this;
    }
    public MessageEmbed build() {
        return builder.build();
    }

    private void base(Color color, String headline) {
        this.builder = new EmbedBuilder();
        this.builder.setColor(color);
        this.builder.setTitle(headline);
    }

    public static Color psw2Color = Color.getHSBColor(24, 100, 100);
}
