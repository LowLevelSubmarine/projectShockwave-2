package listeners;

import core.Main;
import database.Data;
import messages.MsgsReady;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.util.concurrent.TimeUnit;

public class LReady extends ListenerAdapter {
    public void onReady(ReadyEvent event) {
        notifyAllGuilds();
    }

    private static void notifyAllGuilds() {
        for (Guild guild : Main.JDA.getGuilds()) {
            TextChannel textChannel = Data.guild(guild).getNotifychannel();
            textChannel.sendMessage(MsgsReady.startup()).queue(m -> m.delete().queueAfter(10, TimeUnit.SECONDS));
        }
    }
}
