package commands.information;

import commands.CommandInfo;
import commands.CommandInterface;
import commands.SecurityLevel;
import core.Statics;
import core.VersionInfo;
import messages.MsgBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;

public class Changelog implements CommandInterface {
    @Override
    public SecurityLevel securityLevel() {
        return SecurityLevel.NONE;
    }

    @Override
    public void run(CommandInfo info) {
        //Get changelog from VersionInfo.class
        String changelog = VersionInfo.CHANGELOG;

        //Send message
        MessageEmbed embed = MsgBuilder.changelog(changelog);
        info.getChannel().sendMessage(embed).queue();
    }

    @Override
    public String title() {
        return "Die letzten Änderungen";
    }

    @Override
    public String description() {
        return "Gibt die Änderungen seit der letzten Version von \"" + Statics.TITLE + "\" an";
    }

    @Override
    public String syntax(String p) {
        return p + "changelog";
    }
}
