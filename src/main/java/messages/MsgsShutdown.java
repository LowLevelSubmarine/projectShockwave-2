package messages;

import net.dv8tion.jda.core.entities.MessageEmbed;

public class MsgsShutdown {
    public static MessageEmbed shutdown(String shutdownReason) {
        MsgBuilder builder = new MsgBuilder(MsgBuilder.psw2Color, "\uD83D\uDD3B", "SHUTTING DOWN");
        builder.setDescription("ProjectShockwave wird umgehend heruntergefahren.");
        if (shutdownReason != null) builder.addField("Grund", shutdownReason, false);
        return builder.build();
    }
}
