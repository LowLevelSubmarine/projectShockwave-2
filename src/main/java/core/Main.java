package core;

import commands.handling.CommandHandler;
import commands.administration.Restart;
import commands.administration.Shutdown;
import commands.administration.Speedtest;
import commands.administration.Statistics;
import commands.music.*;
import commands.statistic_handling.GuildDetails;
import commands.statistic_handling.RunningOn;
import commands.statistic_handling.StatHandler;
import commands.statistic_handling.UserDetails;
import commands.information.*;
import commands.settings.*;
import data.DATA;
import data.config.BotUser;


public class Main {

    private static String TOKEN;

    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new ShutdownHook());
        addCommands();
        addStatistics();
        parseToken();
        BotHandler.boot();
    }

    public static String getToken() {
        return TOKEN;
    }

    private static void parseToken() {
        DATA.boot();
        BotUser botUser = DATA.config().getBotUser();
        TOKEN = botUser.getToken();
        NotifyConsole.log(Main.class, VersionInfo.PROJECTTITLE + " is using the \"" + botUser.getName() + "\" token");
    }

    public static void addCommands() {
        //Administration
        CommandHandler.addCommand(new Restart());
        CommandHandler.addCommand(new Shutdown());
        CommandHandler.addCommand(new Speedtest());
        CommandHandler.addCommand(new Statistics());
        //Information
        CommandHandler.addCommand(new Changelog());
        CommandHandler.addCommand(new Help());
        CommandHandler.addCommand(new Info());
        CommandHandler.addCommand(new Invite());
        CommandHandler.addCommand(new Permissions());
        CommandHandler.addCommand(new Ping());
        CommandHandler.addCommand(new Version());
        //Music
        CommandHandler.addCommand(new Pause());
        CommandHandler.addCommand(new Play());
        CommandHandler.addCommand(new Search());
        CommandHandler.addCommand(new Skip());
        CommandHandler.addCommand(new Skipplaylist());
        CommandHandler.addCommand(new Stop());
        //Settings
        CommandHandler.addCommand(new SetAudioBuffer());
        CommandHandler.addCommand(new SetBotadmins());
        CommandHandler.addCommand(new SetBotSnappys());
        CommandHandler.addCommand(new SetMusicChannel());
        CommandHandler.addCommand(new SetNotifyChannel());
        CommandHandler.addCommand(new SetPrefix());
        CommandHandler.addCommand(new SetServerSnappys());
        CommandHandler.addCommand(new SetSnappys());
        CommandHandler.addCommand(new SetStatus());
        CommandHandler.addCommand(new SetVolume());
    }

    private static void addStatistics() {
        StatHandler.addStatInterface(new RunningOn());
        StatHandler.addStatInterface(new GuildDetails());
        StatHandler.addStatInterface(new UserDetails());
    }
}
