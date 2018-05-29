package core;

import commands.statistic_handling.StatHandler;
import data.DATA;
import listeners.ExceptionListener;
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

    private static final int NONDEBUGSHUTDOWNtIME = 10;
    private static final int DEBUGSHUTDOWNTIME = 2;
    private static int SHUTDOWNTIME;
    private static JDA JDA;

    //Boots everything connected to the DiscordAPI up
    public static void boot() {
        if (preBootOperations()) {
            //Set JDA
            JDABuilder builder = new JDABuilder(AccountType.BOT);
            builder.setToken(Main.getToken());
            builder.setStatus(OnlineStatus.ONLINE);
            builder.setGame(DATA.bot().getGame());
            builder.setAutoReconnect(true);
            builder.setEnableShutdownHook(false);

            //Add listeners
            builder.addEventListener(new ExceptionListener());
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

    private static boolean preBootOperations() {
        setShutdownTime();
        boolean success = DATA.boot();
        StatHandler.boot();
        return success;
    }

    //Shuts everything connected to the DiscordAPI entirely down
    public static void shutdown(String reason) {
        if (JDAHandler.isRunning()) {
            notifyAboutShudown(reason);
            StatHandler.shutdown();
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

    public static void setJDA(JDA jda) {
        JDA = jda;
    }

    public static String getUsername() {
        if (isRunning()) return JDA.getSelfUser().getName();
        return "MISSING_USERNAME";
    }

    private static void setShutdownTime() {
        if (DATA.config().debugMode()) {
            SHUTDOWNTIME = DEBUGSHUTDOWNTIME;
        } else {
            SHUTDOWNTIME = NONDEBUGSHUTDOWNtIME;
        }
    }

    private static void notifyAboutShudown(String reason) {
        MessageEmbed embed = MsgBuilder.shutdownNotification(reason, SHUTDOWNTIME);
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
