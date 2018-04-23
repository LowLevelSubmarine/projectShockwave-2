package core;

import database.Data;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;

import javax.security.auth.login.LoginException;

public class Main {

    public static JDA JDA;

    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new ShutdownHook());
        buildJDA();
    }

    private static void buildJDA() {
        JDABuilder builder = new JDABuilder(AccountType.BOT);
        builder.setToken(Data.config().getToken());
        builder.setStatus(OnlineStatus.ONLINE);
        builder.setGame(Data.bot().getGame());
        builder.setAutoReconnect(true);
        builder.setEnableShutdownHook(false);

        try {
            JDA = builder.buildBlocking();
        } catch (LoginException e) {
            System.out.println("The given token is invalid");
        } catch (InterruptedException e) {
            System.out.println("An Error accoured while connecting to the Discord API.\nFor more informaiton visit status.discordapp.com");
        }
    }
    private static void shutdownJDA() {
        ShutdownHook.shutdownJDA();
    }
    public static void restartJDA() {
        shutdownJDA();
        buildJDA();
    }
}
