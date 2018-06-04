package messages;

import net.dv8tion.jda.core.entities.Message;

public class MsgUtils {
    public static void addReactions(Message m, String... reactions) {
        for (String reaction : reactions) {
            m.addReaction(reaction).queue();
        }
    }
}
