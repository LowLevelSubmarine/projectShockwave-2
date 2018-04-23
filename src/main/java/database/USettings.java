package database;

import net.dv8tion.jda.core.entities.User;

public class USettings {

    private User user;

    public USettings(User user) {
        this.user = user;
    }
    public enum keys {
        MAP,
    }
}
