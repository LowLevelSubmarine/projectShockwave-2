package core;

import commands.CommandHandler;
import commands.administration.Restart;
import commands.administration.Shutdown;
import commands.information.*;
import database.DATA;
import database.config.BotUser;

import java.security.Permission;

public class Main {

    private static String TOKEN;

    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new ShutdownHook());
        addCommands();
        parseToken();
        JDAHandler.boot();
    }

    public static String getToken() {
        return TOKEN;
    }

    private static void parseToken() {
        DATA.boot();
        BotUser botUser = DATA.config().getBotUser();
        DATA.shutdown();
        TOKEN = botUser.getToken();
        NotifyConsole.log("Main.class", Statics.TITLE + " is using the \"" + botUser.getName() + "\" token");
    }

    private static void addCommands() {
        CommandHandler.addCommand("restart", Restart.class);
        CommandHandler.addCommand("shutdown", Shutdown.class);
        //CommandHandler.addCommand("speedtest", Speedtest.class);
        CommandHandler.addCommand("info", Info.class);
        CommandHandler.addCommand("help", Help.class);
        CommandHandler.addCommand("ping", Ping.class);
        CommandHandler.addCommand("invite", Invite.class);
        CommandHandler.addCommand("permissions", Permissions.class);
        CommandHandler.addCommand("version", Version.class);
        CommandHandler.renderCommandList();
    }
}
