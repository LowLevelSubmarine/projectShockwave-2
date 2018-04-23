package database;

import com.toddway.shelf.ShelfItem;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.TextChannel;

public class GSettings {

    private Guild guild;

    public GSettings(Guild guild) {
        this.guild = guild;
    }

    public TextChannel getNotifychannel() {
        ShelfItem item = Data.getGuildItem(keys.NOTIFYCHANNEL);
        if (item.exists()) return item.get(TextChannel.class);
        else return this.guild.getDefaultChannel();
    }

    public enum keys {
        PREFIX, NOTIFYCHANNEL,
    }
}
