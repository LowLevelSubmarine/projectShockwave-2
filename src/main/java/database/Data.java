package database;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.ConversionException;
import com.toddway.shelf.Shelf;
import com.toddway.shelf.ShelfItem;
import core.JDAHandler;
import core.NotifyConsole;
import database.config.Config;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.User;

import java.io.*;

public class DATA {

    private static final String ORIGINPATH = "PSWD2";
    private static final String UPATH = "USER";
    private static final String GPATH = "GUILD";
    private static final String BPATH = "BOT";
    private static Shelf USHELF = new Shelf(new File(ORIGINPATH + File.separator + UPATH));
    private static Shelf GSHELF = new Shelf(new File(ORIGINPATH + File.separator + GPATH));
    private static Shelf BSHELF = new Shelf(new File(ORIGINPATH + File.separator + BPATH));
    private static final String CONFIGPATH = "config.xml";
    private static File CONFIGFILE = new File(CONFIGPATH);
    private static Config CONFIG;
    private static XStream XSTREAM = Config.buildCompatibleXStream();

    public static boolean boot() {
        try {

            if (!CONFIGFILE.exists()) {
                FileWriter writer = new FileWriter(CONFIGFILE);
                XSTREAM.toXML(new Config(), writer);
                writer.close();
            }
            FileReader reader = new FileReader(CONFIGFILE);
            CONFIG = (Config) XSTREAM.fromXML(reader);
            reader.close();

            String flaws = CONFIG.getFlaws();
            if (flaws != null) {
                System.out.println(flaws);
                return false;
            }
            return true;
        } catch (ConversionException e) {
            e.printStackTrace();
            NotifyConsole.log("data.class", "An error accoured while reading the \"" + CONFIGPATH + "\" file. " +
                    "Try deleting the File before running projectShockwave again.");
        } catch (IOException e) {
            JDAHandler.fatalError();
        }
        return false;
    }
    public static void shutdown() {

    }

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
