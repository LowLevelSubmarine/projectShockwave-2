package commands.music_handling;

import data.DATA;
import net.dv8tion.jda.core.entities.Member;

import java.util.HashMap;

public class SnappyConverter {
    public static String convert(String key, Member member) {
        HashMap<String, String> botSnappys = DATA.bot().getSnappys();
        if (botSnappys.containsKey(key)) {
            return botSnappys.get(key);
        }
        HashMap<String, String> guildSnappys = DATA.guild(member.getGuild()).getSnappys();
        if (guildSnappys.containsKey(key)) {
            return guildSnappys.get(key);
        }
        HashMap<String, String> userSnappys = DATA.user(member.getUser()).getSnappys();
        return userSnappys.getOrDefault(key, key);
    }
}
