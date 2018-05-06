package core;

import database.DATA;
import listeners.GenericGuildMessageReactionListener;
import listeners.GuildMessageReceivedListener;
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

public class JDAHandler {

    private static final int RESTARTTIME = 10;
    private static final int SHUTDOWNTIME = 10;
    private static String REASON;
    private static JDA JDA;

    //Boots everything connected to the DiscordAPI up
    public static void boot() {
        if (bootPreOperations()) {
            //Set JDA
            JDABuilder builder = new JDABuilder(AccountType.BOT);
            builder.setToken(Main.getToken());
            builder.setStatus(OnlineStatus.ONLINE);
            builder.setGame(DATA.bot().getGame());
            builder.setAutoReconnect(true);
            builder.setEnableShutdownHook(false);

            //Add listeners
            builder.addEventListener(new GenericGuildMessageReactionListener());
            builder.addEventListener(new GuildMessageReceivedListener());
            builder.addEventListener(new ReadyListener());

            //Build JDA
            try {
                JDA = builder.buildBlocking();
            } catch (LoginException e) {
                System.out.println("The given token is invalid");
            } catch (InterruptedException e) {
                System.out.println("An error accoured while connecting to the Discord API.\nFor more informaiton visit status.discordapp.com");
            }
            NotifyConsole.log(JDAHandler.class, Statics.TITLE + " is up and running");
        }
    }

    private static boolean bootPreOperations() {
        return DATA.boot();
    }

    //Shuts everything connected to the DiscordAPI entirely down
    public static void shutdown(String reason) {
        if (JDAHandler.isRunning()) {
            REASON = reason;
            notifyAboutShudown();
            JDA.shutdown();
            JDA = null;
            NotifyConsole.log(JDAHandler.class, Statics.TITLE + " is shutting down");
        }
    }

    public static void restart(String reason) {
        shutdown(reason);
        boot();
    }



    public static boolean isRunning() {
        return JDA != null;
    }
    public static JDA getJDA() {
        return JDA;
    }
    public static void updateJDA(JDA jda) {
        JDA = jda;
    }
    public static void fatalError() {
        System.out.println("Es gab einen fatalen Fehler beim ausführen des Codes");
        System.exit(1);
    }

    private static void notifyAboutShudown() {
        MessageEmbed embed = MsgBuilder.shutdownNotification(REASON, SHUTDOWNTIME);
        notifyGuildsAndWait(embed, SHUTDOWNTIME);
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
