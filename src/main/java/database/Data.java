package database;

import com.toddway.shelf.Shelf;
import com.toddway.shelf.ShelfItem;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.User;

import java.io.File;

public class Data {
    private static String ORIGINPATH = "PSWD2/";
    private static String UPATH = "USER";
    private static String GPATH = "GUILD";
    private static String BPATH = "BOT";
    private static Shelf USHELF = new Shelf(new File(ORIGINPATH + UPATH));
    private static Shelf GSHELF = new Shelf(new File(ORIGINPATH + GPATH));
    private static Shelf BSHELF = new Shelf(new File(ORIGINPATH + BPATH));
    private static Config CONFIG;

    public static USettings user(User user) {
        return new USettings(user);
    }
    public static GSettings guild(Guild guild) {
        return new GSettings(guild);
    }
    public static BSettings bot() {
        return new BSettings();
    }
    public static Config config() {
        return CONFIG;
    }

    static ShelfItem getUserItem(USettings.keys key, User user) {
        return USHELF.item(user.getId() + "_" + key.name());
    }
    static ShelfItem getGuildItem(GSettings.keys key, Guild guild) {
        return GSHELF.item(guild.getId() + "_" + key.name());
    }
    static ShelfItem getBotItem(BSettings.keys key) {
        return BSHELF.item(key.name());
    }
}
