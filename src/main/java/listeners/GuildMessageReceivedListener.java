package listeners;

import commands.handling.CommandHandler;
import commands.handling.CommandInfo;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class GuildMessageReceivedListener extends ListenerAdapter {
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        if (event.getAuthor().isBot()) {
            return;
        }
        CommandInfo cmdInfo = new CommandInfo(event);
        if (cmdInfo.isCommand()) {
            CommandHandler.fire(cmdInfo);
        }
    }
}
