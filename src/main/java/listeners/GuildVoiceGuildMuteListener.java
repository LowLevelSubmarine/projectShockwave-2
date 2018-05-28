package listeners;

import commands.music_handling.PlayerHandler;
import commands.music_handling.GuildPlayer;
import net.dv8tion.jda.core.events.guild.voice.GuildVoiceGuildMuteEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class GuildVoiceGuildMuteListener extends ListenerAdapter {
    public void onGuildVoiceGuildMute(GuildVoiceGuildMuteEvent event) {
        if (event.getMember() != event.getGuild().getSelfMember()) {
            GuildPlayer guildPlayer = PlayerHandler.getQueue(event.getGuild());
            if (guildPlayer != null) {
                guildPlayer.setPaused(event.isGuildMuted());
            }
        }
    }
}
