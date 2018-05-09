package database.config;

import com.thoughtworks.xstream.XStream;
import tools.Toolkit;
import net.dv8tion.jda.core.entities.Game;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Config {
    private String autoselectBotUser;
    private List<BotUser> botUsers;
    private List<String> owners;
    private String hoster;
    private String backupPrefix;
    private String backupGame;
    private String debugMode;
    private List<String> debugSupporters;

    public Config() {
        this.autoselectBotUser = "the token to be selected automatically";
        this.botUsers = new ArrayList<>();
        this.botUsers.add(new BotUser());
        this.owners = new ArrayList<>();
        this.owners.add("user-id of a bot owner");
        this.hoster = "user-id of the bot hoster";
        this.backupPrefix = "the backupPrefix";
        this.backupGame = "The status";
        this.debugMode = "false or true";
        this.debugSupporters = new ArrayList<>();
        this.debugSupporters.add("user-id of a debug-supporter");
    }

    public static XStream buildCompatibleXStream() {
        XStream xStream = new XStream();
        Class[] classes = new Class[] {Config.class, BotUser.class};
        XStream.setupDefaultSecurity(xStream);
        xStream.allowTypes(classes);

        xStream.alias("botUser", BotUser.class);
        xStream.alias("config", Config.class);
        return xStream;
    }



    public String getFlaws() {
        //Check if "autoselectBotUser" is a number
        try {
            Integer.parseInt(this.autoselectBotUser);
        } catch (NumberFormatException e) {
            return "The field \"autoselectBotUser\" inside of the config-file is not a valid number";
        }

        //Check if "botUsers" is not empty and all values are valid
        if (this.botUsers.size() <= 0) {
            return "The field \"botUsers\" inside of the config-file is empty";
        }
        for (BotUser botUser : this.botUsers) {
            String validation = botUser.isValid();
            if (validation != null) return "One field of the \"botUsers\" inside of the config-file is not valid: " + validation;
        }

        //Check if "owners" is empty and all values are filled
        if (this.owners.size() <= 0) {
            return "The field \"owners\" inside of the config-file is empty";
        }
        for (String debugSupporter : owners) {
            if (debugSupporter.isEmpty()) {
                return "One field of the \"owners\" is empty";
            }
        }

        //Check if "hoster" is empty
        if (this.hoster.isEmpty()) {
            return "The field \"hoster\" inside of the config-file is empty";
        }

        //Check if "backupPrefix" is emtpy
        if (this.backupPrefix.isEmpty()) {
            return "The field \"backupPrefix\" inside of the config-file is empty";
        }

        //Check if "backupGame" is empty
        if (this.backupGame.isEmpty()) {
            return "The field \"backupGame\" inside of the config-file is empty";
        }

        //Check if "debugMode" is empty or neither equals true nor false
        if (this.debugMode.isEmpty()) {
            return "The field \"debugMode\" inside of the config-file is empty";
        }
        if (!(this.debugMode.toLowerCase().equals("true") || this.debugMode.toLowerCase().equals("false"))) {
            return "The field \"debugMode\" inside of the config-file is neither true nor false";
        }

        //Check if "debugSupporters" is empty and all values are filled
        if (this.debugSupporters.size() <= 0) {
            return "The field \"debugSupporters\" inside of the config-file is empty";
        }
        for (String debugSupporter : debugSupporters) {
            if (debugSupporter.isEmpty()) {
                return "One field of the \"debugSupporters\" is empty";
            }
        }

        //In case everything is valid
        return null;
    }


    public BotUser getBotUser() {
        int selection = Integer.parseInt(this.autoselectBotUser) - 1;
        while (!tokenExists(selection)) {
            System.out.println("Please choose one of the following tokens");
            String options = "";
            for (int i = 0; i < this.botUsers.size(); i++) {
                options += i + 1 + ": " +  this.botUsers.get(i).getName() + "\n";
            }
            System.out.print(options);
            selection = new Scanner(System.in).nextInt() - 1;
        }
        return this.botUsers.get(selection);
    }
    private boolean tokenExists(int index) {
        return -1 < index && index < this.botUsers.size();
    }

    public List<String> getOwners() {
        return this.owners;
    }

    public String getHoster() {
        return this.hoster;
    }

    public String getBackupPrefix() {
        return this.backupPrefix;
    }

    public Game getBackupGame() {
        return Game.playing(this.backupGame);
    }

    public boolean debugMode() {
        return Toolkit.getAsBoolean(this.debugMode);
    }

    public List<String> getDebugSupporters() {
        return this.debugSupporters;
    }
}
