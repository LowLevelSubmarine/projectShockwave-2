package listeners;

import commands.handling.CommandHandler;
import core.JDAHandler;
import data.DATA;
import messages.MsgBuilder;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.util.concurrent.TimeUnit;

public class ReadyListener extends ListenerAdapter {

    private static final int DELETEMESSAGEAFTER = 10;

    public void onReady(ReadyEvent event) {
        JDAHandler.setJDA(event.getJDA());
        CommandHandler.renderCommandList();
        notifyAboutBootup(event.getJDA());
    }

    private static void notifyAboutBootup(JDA jda) {
        for (Guild guild : jda.getGuilds()) {
            TextChannel textChannel = DATA.guild(guild).getNotifychannel();
            textChannel.sendMessage(MsgBuilder.bootupNotification()).queue(m -> m.delete().queueAfter(DELETEMESSAGEAFTER, TimeUnit.SECONDS));
        }
    }
}
