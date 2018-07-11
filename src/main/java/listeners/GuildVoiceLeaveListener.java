package listeners;

import commands.music_handling.IdleChecker;
import net.dv8tion.jda.core.events.guild.voice.GuildVoiceLeaveEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class GuildVoiceLeaveListener extends ListenerAdapter {
    public void onGuildVoiceLeave(GuildVoiceLeaveEvent event) {
        if (!event.getMember().getUser().isBot()) {
            new Thread(new IdleChecker(event.getGuild())).start();
        }
    }
}
