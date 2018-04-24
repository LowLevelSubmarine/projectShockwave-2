package database;

import com.toddway.shelf.ShelfItem;
import net.dv8tion.jda.core.entities.Game;

public class BSettings {

    public Game getGame() {
        ShelfItem item = DATA.getBotItem(keys.GAME);
        if (item.exists()) return item.get(Game.class);
        else return DATA.config().getBackupGame();
    }

    public enum keys {
        GAME,
    }
}
