package core;

import net.dv8tion.jda.core.entities.User;

public class InviteGenerator {

    private static final String URL_HEAD = "https://discordapp.com/api/oauth2/authorize?client_id=";
    private static final String URL_TAIL = "&permissions=8&scope=bot";

    public static String get(User user) {
        if (!user.isBot()) {
            return null;
        } else {
            return URL_HEAD + user.getId() + URL_TAIL;
        }
    }
}
