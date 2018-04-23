package core;

import database.Data;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Message;
import messages.MsgsShutdown;
import net.dv8tion.jda.core.entities.TextChannel;

import java.util.LinkedList;
import java.util.List;

public class ShutdownHook extends Thread {

    private static String shutdownReason;

    @Override
    public void run() {
        notifyAllGuilds();
        shutdownJDA();
    }

    //Shuts everything connected to the DiscordAPI or the DiscordJDA entirely down
    static void shutdownJDA() {
        Main.JDA.shutdown();
    }

    private static void notifyAllGuilds() {
        //List of Messages wich should later be deleted
        List<Message> messages = new LinkedList<>();

        //Send all Messages
        for (Guild guild : Main.JDA.getGuilds()) {
            TextChannel textChannel = Data.guild(guild).getNotifychannel();
            Message message = textChannel.sendMessage(MsgsShutdown.shutdown(shutdownReason)).complete();
            messages.add(message);
        }

        //Wait 10 Seconds
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Delete all Messages
        for (Message message : messages) {
            message.delete().complete();
        }
    }
}
