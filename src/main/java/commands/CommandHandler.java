package commands;

import messages.MsgBuilder;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.util.HashMap;

public class CommandHandler {
    private static HashMap<String, Class> COMMANDS = new HashMap<>();

    public static void fire(GuildMessageReceivedEvent event) {
        CommandInfo info = new CommandInfo(event);
        //Checks if command exists
        if (COMMANDS.containsKey(info.getInvoke())) {
            try {
                CommandInterface cmdInterface = (CommandInterface) COMMANDS.get(info.getInvoke()).newInstance();
                checkPermissionAndRun(cmdInterface, event, info);
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }

    }

    public static void addCommand(String invoke, Class command) {
        COMMANDS.put(invoke, command);
    }



    private static void checkPermissionAndRun(CommandInterface cmdInterface, GuildMessageReceivedEvent event, CommandInfo info) {
        SecurityLevel securityLevel = cmdInterface.securityLevel();
        if (securityLevel.isAuthorized(event.getMember())) {
            cmdInterface.run(info);
        } else {
            event.getChannel().sendMessage(MsgBuilder.missingAuthorization()).queue();
        }
    }
}
