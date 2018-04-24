package database;

import database.config.TokenPair;
import net.dv8tion.jda.core.entities.Game;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Config {
    private String autoselectToken;
    private List<TokenPair> tokenPairs;
    private String backupGame;

    public Config() {
        this.autoselectToken = "The token to be selected automatically";
        this.tokenPairs = new ArrayList<>();
        this.tokenPairs.add(new TokenPair());
        this.backupGame = "The status";
    }


    public boolean valid() {
        //Check if "autoselectToken" is a number
        try {
            Integer.parseInt(this.autoselectToken);
        } catch (NumberFormatException e) {
            System.out.println("The field \"autoselectToken\" inside of the config-file is not a valid number");
            return false;
        }
        return true;
    }


    public TokenPair getTokenPair() {
        int selection = Integer.parseInt(this.autoselectToken) - 1;
        while (!tokenExists(selection)) {
            System.out.println("Please choose one of the following tokens");
            String options = "";
            for (int i = 0; i < this.tokenPairs.size(); i++) {
                options += i + 1 + ": " +  this.tokenPairs.get(i).getName() + "\n";
            }
            System.out.print(options);
            selection = new Scanner(System.in).nextInt() - 1;
            System.out.println(selection);
        }
        return this.tokenPairs.get(selection);
    }
    private boolean tokenExists(int index) {
        return -1 < index && index < this.tokenPairs.size();
    }

    public Game getBackupGame() {
        return Game.playing(this.backupGame);
    }
}
