package database;

import com.toddway.shelf.Shelf;
import com.toddway.shelf.ShelfItem;
import net.dv8tion.jda.core.entities.Game;

public class BSettings {
    private Shelf shelf;

    public Game getGame() {
        ShelfItem item = Data.getBotItem(keys.GAME);
        if (item.exists()) return item.get(Game.class);
        else return Data.config().getDefaultGame();
    }

    public enum keys {
        GAME,
    }
}
