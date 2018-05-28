package commands.settings;

import commands.handling.CommandInfo;
import commands.handling.CommandInterface;
import commands.handling.CommandType;
import commands.handling.SecurityLevel;
import tools.Toolkit;
import data.DATA;
import messages.MsgBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.User;

import java.util.ArrayList;
import java.util.List;

public class SetBotadmins implements CommandInterface {
    @Override
    public String invoke() {
        return "setbotadmins";
    }

    @Override
    public CommandType type() {
        return CommandType.SETTINGS;
    }

    @Override
    public SecurityLevel securityLevel() {
        return SecurityLevel.OWNER;
    }

    @Override
    public void run(CommandInfo info) {
        String method = info.getArgument(1);
        List<User> mentionedUsers = info.getMessage().getMentionedUsers();
        if (method == null || !Toolkit.isOneOf(method.toLowerCase(), "add", "remove") || mentionedUsers.size() != 1) {
            info.wrongSyntax();
            return;
        }

        if (method.toLowerCase().equals("add")) {
            addBotadmin(mentionedUsers.get(0));
            MessageEmbed embed = MsgBuilder.addBotadminDone(mentionedUsers.get(0));
            info.getChannel().sendMessage(embed).queue();
        } else {
            removeBotadmin(mentionedUsers.get(0));
            MessageEmbed embed = MsgBuilder.removeBotadminDone(mentionedUsers.get(0));
            info.getChannel().sendMessage(embed).queue();
        }
    }

    @Override
    public String title() {
        return "Verteilt die " + SecurityLevel.BOT.getTitle() + " Berechtigung";
    }

    @Override
    public String description() {
        return "Zum verteilen oder entfernen der " + SecurityLevel.BOT.getTitle() + " Berechtigung";
    }

    @Override
    public String syntax(String p) {
        return p + "setbotadmins < add | remove > < @mention >";
    }



    private static void addBotadmin(User user) {
        ArrayList<String> botadmins = DATA.bot().getBotadmins();
        botadmins.add(user.getId());
        DATA.bot().setBotadmins(botadmins);
    }
    private static void removeBotadmin(User user) {
        ArrayList<String> botadmins = DATA.bot().getBotadmins();
        botadmins.remove(user.getId());
        DATA.bot().setBotadmins(botadmins);
    }
}
