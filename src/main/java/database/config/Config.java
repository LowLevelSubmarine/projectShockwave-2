package database.config;

import com.thoughtworks.xstream.XStream;
import core.Toolkit;
import net.dv8tion.jda.core.entities.Game;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Config {
    private String autoselect_bot_user;
    private List<BotUser> bot_users;
    private String prefix;
    private String backup_game;
    private String debug_mode;
    private List<String> debug_supporters;

    public Config() {
        this.autoselect_bot_user = "the token to be selected automatically";
        this.bot_users = new ArrayList<>();
        this.bot_users.add(new BotUser());
        this.prefix = "the prefix";
        this.backup_game = "The status";
        this.debug_mode = "false or true";
        this.debug_supporters = new ArrayList<>();
        this.debug_supporters.add("user-id of a debug-supporter");
    }

    public static XStream buildCompatibleXStream() {
        XStream xStream = new XStream();
        Class[] classes = new Class[] {Config.class, BotUser.class};
        XStream.setupDefaultSecurity(xStream);
        xStream.allowTypes(classes);

        xStream.alias("bot_user", BotUser.class);
        xStream.alias("config", Config.class);
        return xStream;
    }



    public boolean valid() {
        //Check if "autoselect_bot_user" is a number
        try {
            Integer.parseInt(this.autoselect_bot_user);
        } catch (NumberFormatException e) {
            System.out.println("The field \"autoselect_bot_user\" inside of the config-file is not a valid number");
            return false;
        }
        if (this.prefix.isEmpty()) {
            System.out.println("The field \"prefix\" inside of the config-file is empty");
            return false;
        }
        return true;
    }


    public BotUser getTokenPair() {
        int selection = Integer.parseInt(this.autoselect_bot_user) - 1;
        while (!tokenExists(selection)) {
            System.out.println("Please choose one of the following tokens");
            String options = "";
            for (int i = 0; i < this.bot_users.size(); i++) {
                options += i + 1 + ": " +  this.bot_users.get(i).getName() + "\n";
            }
            System.out.print(options);
            selection = new Scanner(System.in).nextInt() - 1;
            System.out.println(selection);
        }
        return this.bot_users.get(selection);
    }
    private boolean tokenExists(int index) {
        return -1 < index && index < this.bot_users.size();
    }

    public String getPrefix() {
        return this.prefix;
    }

    public Game getBackupGame() {
        return Game.playing(this.backup_game);
    }

    public boolean debugMode() {
        return Toolkit.getAsBoolean(this.debug_mode);
    }

    public List<String> getDebugSupporters() {
        return this.debug_supporters;
    }
}
