package database;

import com.toddway.shelf.ShelfItem;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.entities.User;

import java.util.ArrayList;

public class BSettings {

    public Game getGame() {
        ShelfItem item = DATA.getBotItem(keys.GAME);
        if (item.exists()) return item.get(Game.class);
        else return DATA.config().getBackupGame();
    }
    public void setGame(Game game) {
        ShelfItem item = DATA.getBotItem(keys.GAME);
        item.put(game);
    }

    public ArrayList<User> getBotAdmins() {
        ShelfItem item = DATA.getBotItem(keys.BOTADMINS);
        if (item.exists()) return item.get(ArrayList.class);
        else return new ArrayList<>();
    }
    public void setBotAdmins(ArrayList<User> botAdmins) {
        ShelfItem item = DATA.getBotItem(keys.BOTADMINS);
        item.put(botAdmins);
    }

    public enum keys {
        GAME, BOTADMINS,
    }
}
