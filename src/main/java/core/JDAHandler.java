package core;

import database.DATA;
import database.config.TokenPair;
import listeners.ReadyListener;
import messages.MsgBuilder;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.TextChannel;

import javax.security.auth.login.LoginException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class JDAHandler {

    private static final int RESTARTTIME = 10;
    private static final int SHUTDOWNTIME = 10;
    private static String REASON;
    private static JDA JDA;

    //Boots everything connected to the DiscordAPI up
    public static void boot() {
        //Required pre-boot-operations
        if (!DATA.boot()) {
            return;
        }

        //Set JDA
        TokenPair tokenPair = DATA.config().getTokenPair();
        JDABuilder builder = new JDABuilder(AccountType.BOT);
        builder.setToken(tokenPair.getToken());
        builder.setStatus(OnlineStatus.ONLINE);
        builder.setGame(DATA.bot().getGame());
        builder.setAutoReconnect(true);
        builder.setEnableShutdownHook(false);
        builder.addEventListener(new ReadyListener());

        //Build JDA
        System.out.println("ProjectShockwave is booting as " + tokenPair.getName());
        try {
            JDA = builder.buildBlocking();
        } catch (LoginException e) {
            System.out.println("The given token is invalid");
        } catch (InterruptedException e) {
            System.out.println("An Error accoured while connecting to the Discord API.\nFor more informaiton visit status.discordapp.com");
        }
    }

    //Shuts everything connected to the DiscordAPI entirely down
    public static void shutdown(String reason) {
        if (JDAHandler.isRunning()) {
            REASON = reason;
            notifyAboutShudown();
            JDA.shutdown();
            DATA.shutdown();
            JDA = null;
        }
    }

    public static void restart(String reason) {
        REASON = reason;
    }



    public static boolean isRunning() {
        return JDA != null;
    }
    public static JDA getJDA() {
        return JDA;
    }
    public static void fatalError() {
        System.out.println("Es gab einen fatalen Fehler beim ausführen des Codes");
        System.exit(1);
    }

    private static void notifyAboutRestart() {
        MessageEmbed embed = MsgBuilder.restartNotification(REASON, RESTARTTIME);
        notifyGuildsAndWait(embed, RESTARTTIME);
    }
    private static void notifyAboutShudown() {
        MessageEmbed embed = MsgBuilder.shutdownNotification(REASON, SHUTDOWNTIME);
        notifyGuildsAndWait(embed, SHUTDOWNTIME);
    }



    private static void notifyGuilds(MessageEmbed embed, int seconds) {
        //Send all messages and queue deletion
        for (Guild guild : JDAHandler.getJDA().getGuilds()) {
            TextChannel textChannel = DATA.guild(guild).getNotifychannel();
            textChannel.sendMessage(embed).queue(m -> m.delete().queueAfter(seconds, TimeUnit.SECONDS));
        }
    }
    private static void notifyGuildsAndWait(MessageEmbed embed, int seconds) {
        //List of Messages wich should later be deleted
        List<Message> messages = new LinkedList<>();

        //Send all Messages
        for (Guild guild : JDAHandler.getJDA().getGuilds()) {
            TextChannel textChannel = DATA.guild(guild).getNotifychannel();
            Message message = textChannel.sendMessage(embed).complete();
            messages.add(message);
        }

        //Wait 10 Seconds
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Delete all Messages
        for (Message message : messages) {
            message.delete().complete();
        }
    }
}
