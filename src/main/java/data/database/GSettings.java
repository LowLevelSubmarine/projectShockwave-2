package data.database;

import com.toddway.shelf.ShelfItem;
import data.DATA;
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

    public HashMap<String, String> getSnappys() {
        ShelfItem item = DATA.getGuildItem(keys.SONGMAP, this.guild);
        if (item.exists()) return item.get(HashMap.class);
        return new HashMap<>();
    }
    public void setSnappys(HashMap<String, String> songMap) {
        ShelfItem item = DATA.getGuildItem(keys.SONGMAP, this.guild);
        item.put(songMap);
    }

    //External volume !!!
    public int getVolume() {
        ShelfItem item = DATA.getGuildItem(keys.VOLUME, this.guild);
        if (item.exists()) {
            return item.get(Integer.class);
        }
        return DATA.config().getBackupVolume();
    }
    public void setVolume(int volume) {
        ShelfItem item = DATA.getGuildItem(keys.VOLUME, this.guild);
        item.put(volume);
    }

    public TextChannel getMusicChannel() {
        ShelfItem item = DATA.getGuildItem(keys.MUSICCHANNEL, this.guild);
        if (item.exists()) {
            return item.get(TextChannel.class);
        }
        return this.guild.getDefaultChannel();
    }
    public void setMusicChannel(TextChannel channel) {
        ShelfItem item = DATA.getGuildItem(keys.MUSICCHANNEL, this.guild);
        item.put(channel);
    }



    public enum keys {
        PREFIX, NOTIFYCHANNEL, SONGMAP, VOLUME, MUSICCHANNEL,
    }
}
