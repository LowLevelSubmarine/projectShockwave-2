package commands.information;

import commands.handling.CommandInfo;
import commands.handling.CommandInterface;
import commands.handling.CommandType;
import commands.handling.SecurityLevel;
import messages.MsgBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;

public class Invite implements CommandInterface {

    private static final String OAUTH2_START = "https://discordapp.com/api/oauth2/authorize?client_id=";
    private static final String OAUTH2_END = "&permissions=8&scope=bot";

    @Override
    public String invoke() {
        return "invite";
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
        //Generate invite-link
        String botId = info.getJDA().getSelfUser().getId();
        String botInvite = OAUTH2_START + botId + OAUTH2_END;

        //Send message
        MessageEmbed embed = MsgBuilder.invite(botInvite);
        info.getChannel().sendMessage(embed).queue();
    }

    @Override
    public String title() {
        return "Generiert einen invite Link";
    }

    @Override
    public String description() {
        return "Generiert einen Link mit welchem der Bot sich zu einem anderen Server einladen lässt";
    }

    @Override
    public String syntax(String p) {
        return p + "invite";
    }
}
