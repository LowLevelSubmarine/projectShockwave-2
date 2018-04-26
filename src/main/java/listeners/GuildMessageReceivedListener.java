package listeners;

import commands.CommandHandler;
import commands.CommandInfo;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class GuildMessageReceivedListener extends ListenerAdapter {
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        CommandInfo cmdInfo = new CommandInfo(event);
        if (cmdInfo.isCommand()) {
            CommandHandler.fire(event);
        }
    }
}
