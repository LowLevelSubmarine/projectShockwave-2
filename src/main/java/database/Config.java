package database;

import net.dv8tion.jda.core.entities.Game;

public class Config {
    private String token;
    private String defaultGame;

    public String getToken() {
        return this.token;
    }
    public Game getDefaultGame() {
        return Game.playing(this.defaultGame);
    }
}
