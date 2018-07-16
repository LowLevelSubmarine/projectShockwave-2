package listeners;

import statics.PermissionChecker;
import net.dv8tion.jda.core.events.guild.GuildJoinEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class GuildJoinListener extends ListenerAdapter {
    public void onGuildJoin(GuildJoinEvent event) {
        PermissionChecker.checkPermission(event.getGuild());
    }
}
