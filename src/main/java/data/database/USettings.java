package data.database;

import com.toddway.shelf.ShelfItem;
import data.DATA;
import net.dv8tion.jda.core.entities.User;

import java.util.HashMap;

public class USettings {

    private User user;

    public USettings(User user) {
        this.user = user;
    }

    public HashMap<String, String> getSnappys() {
        ShelfItem item = DATA.getUserItem(keys.SONGMAP, this.user);
        if (item.exists()) return item.get(HashMap.class);
        return new HashMap<>();
    }
    public void setSnappys(HashMap<String, String> snappys) {
        ShelfItem item = DATA.getUserItem(keys.SONGMAP, this.user);
        item.put(snappys);
    }


    public enum keys {
        SONGMAP,
    }
}
