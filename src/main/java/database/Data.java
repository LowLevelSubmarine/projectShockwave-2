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

    private static final String CONFIGPATH = "config.xml";
    private static final String FOLDERNAME = "PSW2 Database";
    private static final String UFILENAME = "Users";
    private static final String GFILENAME = "Guilds";
    private static final String BFILENAME = "Bot";
    private static Shelf USHELF;
    private static Shelf GSHELF;
    private static Shelf BSHELF;
    private static File CONFIGFILE = new File(CONFIGPATH);
    private static Config CONFIG;
    private static XStream XSTREAM = Config.buildCompatibleXStream();

    public static boolean boot() {
        boolean success;
        createShelfs();
        success = loadConfigXML();
        return success;
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

    private static void createShelfs() {
        USHELF = createShelf(FOLDERNAME, UFILENAME);
        GSHELF = createShelf(FOLDERNAME, GFILENAME);
        BSHELF = createShelf(FOLDERNAME, BFILENAME);
    }
    private static boolean loadConfigXML() {
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
            NotifyConsole.log(DATA.class, "An error accoured while reading the \"" + CONFIGPATH + "\" file. " +
                    "Try deleting the File before running projectShockwave again.");
        } catch (IOException e) {
            JDAHandler.fatalError();
        }
        return false;
    }
    private static Shelf createShelf(String folderName, String fileName) {
        //Create or get folder
        File folder = new File(folderName);
        folder.mkdir();

        //Create or get file
        String path = folderName + "/" + fileName;
        File file = new File(path);

        //Return shelf
        return new Shelf(file);
    }
}
