package commands.information;

import commands.handling.CommandInfo;
import commands.handling.CommandInterface;
import commands.handling.CommandType;
import commands.handling.SecurityLevel;
import core.VersionInfo;
import messages.MsgBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;

public class Version implements CommandInterface {
    @Override
    public String invoke() {
        return "version";
    }

    @Override
    public boolean silent() {
        return false;
    }

    @Override
    public CommandType type() {
        return CommandType.INFORMATION;
    }

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
