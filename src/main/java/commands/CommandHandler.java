package commands;

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
                SecurityLevel secLvl = cmdInterface.securityLevel();
                if (secLvl.isAuthorized(event.getMember())) {
                    cmdInterface.run(info);
                } else {
                    event.getChannel().sendMessage()
                }
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }

    }

    public static void addCommand(String invoke, CommandInterface commandInterface) {
        COMMANDS.put(invoke, commandInterface);
    }
}
