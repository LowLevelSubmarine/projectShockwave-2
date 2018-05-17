package database;

import com.toddway.shelf.ShelfItem;
import commands.administration.statistics.StatisticContainer;
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

    public StatisticContainer getStatisticContainer() {
        ShelfItem item = DATA.getBotItem(keys.STATISTICCONTAINER);
        if (item.exists()) return item.get(StatisticContainer.class);
        return new StatisticContainer();
    }
    public void setStatisticContainer(StatisticContainer statisticContainer) {
        ShelfItem item = DATA.getBotItem(keys.STATISTICCONTAINER);
        item.put(statisticContainer);
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
