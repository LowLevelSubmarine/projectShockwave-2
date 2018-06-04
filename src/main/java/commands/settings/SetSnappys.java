package commands.settings;

import commands.handling.CommandInfo;
import commands.handling.CommandInterface;
import commands.handling.CommandType;
import commands.handling.SecurityLevel;
import data.DATA;
import messages.MsgBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.User;

import java.util.HashMap;

public class SetSnappys implements CommandInterface {

    User user;

    @Override
    public String invoke() {
        return "setsnappys";
    }

    @Override
    public boolean silent() {
        return false;
    }

    @Override
    public CommandType type() {
        return CommandType.SETTINGS;
    }

    @Override
    public SecurityLevel securityLevel() {
        return SecurityLevel.NONE;
    }

    @Override
    public void run(CommandInfo info) {
        //Get the values
        String value = info.getArgument(1);
        String key = info.getRaw(2);

        //Report wrong syntax
        if (key == null) {
            info.wrongSyntax();
            return;
        }

        //Do the math
        this.user = info.getUser();
        MessageEmbed embed;
        if (value != null) {
            addSnappy(key, value);
            embed = MsgBuilder.addedSnappy(key, value);
        } else {
            removeSnappy(key);
            embed = MsgBuilder.removedSnappy(key);
        }
        info.getChannel().sendMessage(embed).queue();
    }

    @Override
    public String title() {
        return "Tracks kürzen";
    }

    @Override
    public String description() {
        return "Kürzt die Eingabe für den play Command auf eine angegebene Zeichenfolge";
    }

    @Override
    public String syntax(String p) {
        return p + "setmap < Schlüssel > < _ | Schlagwort oder Link >";
    }

    private void addSnappy(String key, String value) {
        HashMap songMap = DATA.user(this.user).getSnappys();
        songMap.put(key, value);
        DATA.user(this.user).setSnappys(songMap);
    }

    private void removeSnappy(String key) {
        HashMap songMap = DATA.user(this.user).getSnappys();
        songMap.remove(key);
        DATA.user(this.user).setSnappys(songMap);
    }
}
