package commands.information;

import commands.CommandInfo;
import commands.CommandInterface;
import commands.SecurityLevel;
import core.JDAHandler;
import core.Statics;
import database.DATA;
import messages.MsgBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.User;

public class Info implements CommandInterface {
    @Override
    public SecurityLevel securityLevel() {
        return SecurityLevel.NONE;
    }

    @Override
    public void run(CommandInfo info) {
        //Generate mentions for the hoster and the developer, and get the GamingNation invite
        String hosterId = DATA.config().getHoster();
        User hosterUser = JDAHandler.getJDA().getUserById(hosterId);
        String hosterMention = hosterUser.getAsMention();
        String devId = Statics.CODERUSERID;
        User devUser = JDAHandler.getJDA().getUserById(devId);
        String devMention = devUser.getAsMention();
        String invite = Statics.GAMINGNATIONINVITE;

        //Send message
        MessageEmbed embed = MsgBuilder.info(hosterMention, devMention, invite);
        info.getChannel().sendMessage(embed).queue();
    }

    @Override
    public String category() {
        return "Infos";
    }

    @Override
    public String title() {
        return "Informationen zum Bot";
    }

    @Override
    public String description() {
        return "Informationen zum Bot";
    }

    @Override
    public String syntax(String p) {
        return p + "info";
    }
}
