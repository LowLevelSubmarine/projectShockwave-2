package commands.settings;

import commands.CommandInfo;
import commands.CommandInterface;
import commands.SecurityLevel;
import database.DATA;
import messages.MsgBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.User;

import java.util.List;

public class SetBotadmins implements CommandInterface {
    @Override
    public SecurityLevel securityLevel() {
        return SecurityLevel.OWNER;
    }

    @Override
    public void run(CommandInfo info) {
        String method = info.getArgument(1);
        List<User> mentionedUsers = info.getMessage().getMentionedUsers();
        if (method == null || !(method.toLowerCase().equals("add") || method.toUpperCase().equals("remove")) || mentionedUsers.isEmpty()) {
            info.wrongSyntax();
            return;
        }

        if (method.toLowerCase().equals("add")) {
            DATA.bot().addBotadmin(mentionedUsers.get(0));
            MessageEmbed embed = MsgBuilder.addBotadminDone(mentionedUsers.get(0));
            info.getChannel().sendMessage(embed).queue();
        } else {
            DATA.bot().removeBotadmin(mentionedUsers.get(0));
            MessageEmbed embed = MsgBuilder.removeBotadminDone(mentionedUsers.get(0));
            info.getChannel().sendMessage(embed).queue();
        }
    }

    @Override
    public String category() {
        return "Administration";
    }

    @Override
    public String title() {
        return "Verteilt die " + SecurityLevel.BOT.toString() + " Berechtigung";
    }

    @Override
    public String description() {
        return "Zuu verteilen oder entfernen der " + SecurityLevel.BOT.toString() + " Berechtigung";
    }

    @Override
    public String syntax(String p) {
        return p + "setbotadmins < add | remove > < @mention >";
    }
}
