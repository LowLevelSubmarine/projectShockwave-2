package data.database;

import com.toddway.shelf.ShelfItem;
import commands.statistic_handling.StatContainer;
import data.DATA;
import net.dv8tion.jda.core.entities.Game;

import java.util.ArrayList;
import java.util.HashMap;

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

    public ArrayList<String> getBotadmins() {
        ShelfItem item = DATA.getBotItem(keys.BOTADMINS);
        if (item.exists()) return item.get(ArrayList.class);
        return new ArrayList<>();
    }
    public void setBotadmins(ArrayList<String> botAdmins) {
        ShelfItem item = DATA.getBotItem(keys.BOTADMINS);
        item.put(botAdmins);
    }

    public StatContainer getStatisticContainer() {
        ShelfItem item = DATA.getBotItem(keys.STATISTICCONTAINER);
        if (item.exists()) return item.get(StatContainer.class);
        return new StatContainer();
    }
    public void setStatisticContainer(StatContainer statContainer) {
        ShelfItem item = DATA.getBotItem(keys.STATISTICCONTAINER);
        item.put(statContainer);
    }

    public HashMap<String, String> getSnappys() {
        ShelfItem item = DATA.getBotItem(keys.SONGMAP);
        if (item.exists()) return item.get(HashMap.class);
        return new HashMap<>();
    }
    public void setSnappys(HashMap<String, String> songMap) {
        ShelfItem item = DATA.getBotItem(keys.SONGMAP);
        item.put(songMap);
    }

    public enum keys {
        GAME, BOTADMINS, STATISTICCONTAINER, SONGMAP,
    }
}
