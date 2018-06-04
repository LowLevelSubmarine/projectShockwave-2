package listeners;

import commands.handling.ButtonEvent;
import commands.handling.ButtonHandler;
import net.dv8tion.jda.core.events.message.guild.react.GenericGuildMessageReactionEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class GenericGuildMessageReactionListener extends ListenerAdapter {
    public void onGenericGuildMessageReaction(GenericGuildMessageReactionEvent event) {
        if (!event.getUser().isBot()) {
            ButtonHandler.fire(new ButtonEvent(event));
        }
    }
}
