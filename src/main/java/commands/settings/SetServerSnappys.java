package commands.settings;

import commands.handling.CommandInfo;
import commands.handling.CommandInterface;
import commands.handling.CommandType;
import commands.handling.SecurityLevel;
import data.DATA;
import messages.MsgBuilder;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.MessageEmbed;

import java.util.HashMap;

public class SetServerSnappys implements CommandInterface {

    Guild guild;

    @Override
    public String invoke() {
        return "setserversnappys";
    }

    @Override
    public CommandType type() {
        return CommandType.SETTINGS;
    }

    @Override
    public SecurityLevel securityLevel() {
        return SecurityLevel.GUILD;
    }

    @Override
    public void run(CommandInfo info) {
        //Get the values
        String key = info.getArgument(1);
        String value = info.getRaw(2);

        //Handle wrong syntax
        if (key == null) {
            info.wrongSyntax();
            return;
        }

        //Do the math
        this.guild = info.getGuild();
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
        return p + "setserversnappys < Schlüssel > < _ | Schlagwort oder Link >";
    }

    private void addSnappy(String key, String value) {
        HashMap<String, String> snappys = DATA.guild(this.guild).getSnappys();
        snappys.put(key, value);
        DATA.guild(this.guild).setSnappys(snappys);
    }

    private void removeSnappy(String key) {
        HashMap<String, String> snappys = DATA.guild(this.guild).getSnappys();
        snappys.remove(key);
        DATA.guild(this.guild).setSnappys(snappys);
    }
}
