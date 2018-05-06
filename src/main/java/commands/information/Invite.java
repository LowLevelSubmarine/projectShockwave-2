package commands.information;

import commands.CommandInfo;
import commands.CommandInterface;
import commands.SecurityLevel;
import messages.MsgBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;

public class Invite implements CommandInterface {

    private static final String OAUTH2_START = "https://discordapp.com/api/oauth2/authorize?client_id=";
    private static final String OAUTH2_END = "&permissions=8&scope=bot";

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
    public String category() {
        return "Infos";
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