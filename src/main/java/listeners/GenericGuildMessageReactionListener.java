package listeners;

import commands.ButtonEvent;
import commands.ButtonHandler;
import net.dv8tion.jda.core.events.message.guild.react.GenericGuildMessageReactionEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class GenericGuildMessageReactionListener extends ListenerAdapter {
    public void onGenericGuildMessageReaction(GenericGuildMessageReactionEvent event) {
        ButtonHandler.fire(new ButtonEvent(event));
    }
}
