package commands.information;

import commands.CommandInfo;
import commands.CommandInterface;
import commands.SecurityLevel;
import core.VersionInfo;
import messages.MsgBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;

public class Version implements CommandInterface {
    @Override
    public SecurityLevel securityLevel() {
        return SecurityLevel.NONE;
    }

    @Override
    public void run(CommandInfo info) {
        //Get version name and number
        String versionNumber = VersionInfo.NAME;
        String versionTitle = VersionInfo.TITLE;

        //Build and send message
        MessageEmbed embed = MsgBuilder.version(versionNumber, versionTitle);
        info.getChannel().sendMessage(embed).queue();
    }

    @Override
    public String title() {
        return "Gibt die Version an";
    }

    @Override
    public String description() {
        return "Gibt die Versionsnummer und den Versionstitel an";
    }

    @Override
    public String syntax(String p) {
        return p + "version";
    }
}
