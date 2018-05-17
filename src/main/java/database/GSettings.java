package database;

import com.toddway.shelf.ShelfItem;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.TextChannel;

import java.util.HashMap;

public class GSettings {

    private Guild guild;

    public GSettings(Guild guild) {
        this.guild = guild;
    }



    public String getPrefix() {
        ShelfItem item = DATA.getGuildItem(keys.PREFIX, this.guild);
        if (item.exists()) return item.get(String.class);
        return DATA.config().getBackupPrefix();
    }
    public void setPrefix(String prefix) {
        ShelfItem item = DATA.getGuildItem(keys.PREFIX, this.guild);
        item.put(prefix);
    }

    public TextChannel getNotifychannel() {
        ShelfItem item = DATA.getGuildItem(keys.NOTIFYCHANNEL, this.guild);
        if (item.exists()) return item.get(TextChannel.class);
        return this.guild.getDefaultChannel();
    }
    public void setNotifyChannel(TextChannel notifyChannel) {
        ShelfItem item = DATA.getGuildItem(keys.NOTIFYCHANNEL, this.guild);
        item.put(notifyChannel);
    }

    public HashMap<String, String> getSongMap() {
        ShelfItem item = DATA.getGuildItem(keys.SONGMAP, this.guild);
        if (item.exists()) return item.get(HashMap.class);
        return new HashMap<>();
    }
    public void setSongMap(HashMap<String, String> songMap) {
        ShelfItem item = DATA.getGuildItem(keys.SONGMAP, this.guild);
        item.put(songMap);
    }



    public enum keys {
        PREFIX, NOTIFYCHANNEL, SONGMAP,
    }
}
