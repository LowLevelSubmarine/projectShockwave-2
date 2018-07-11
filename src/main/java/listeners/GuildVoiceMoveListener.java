package listeners;

import commands.music_handling.IdleChecker;
import net.dv8tion.jda.core.events.guild.voice.GuildVoiceMoveEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class GuildVoiceMoveListener extends ListenerAdapter {
    @Override
    public void onGuildVoiceMove(GuildVoiceMoveEvent event) {
        new Thread(new IdleChecker(event.getGuild())).start();
    }
}
