package messages;

import net.dv8tion.jda.core.entities.MessageEmbed;

public class MsgsReady {
    public static MessageEmbed startup() {
        MsgBuilder builder = new MsgBuilder(MsgBuilder.psw2Color, "\uD83D\uDD3A", "BOOTED UP");
        builder.setDescription("ProjectShockwave ist nun Betriebsbereit.");
        return builder.build();
    }
}
