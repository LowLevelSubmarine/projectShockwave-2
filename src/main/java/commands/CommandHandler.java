package commands;

import core.ExceptionLogger;
import messages.MsgBuilder;

import java.util.HashMap;

public class CommandHandler {
    private static HashMap<String, Class> COMMANDS = new HashMap<>();

    public static void fire(CommandInfo info) {
        //Check if command exists
        if (COMMANDS.containsKey(info.getInvoke())) {
            try {
                //Cast command-class to command-interface, check permissions of the member and run
                CommandInterface cmdInterface = (CommandInterface) COMMANDS.get(info.getInvoke()).newInstance();
                checkPermissionAndRun(cmdInterface, info);
            } catch (InstantiationException | IllegalAccessException e) {
                ExceptionLogger.log(e);
            }
        }
    }

    public static void addCommand(String invoke, Class command) {
        COMMANDS.put(invoke, command);
    }

    private static void checkPermissionAndRun(CommandInterface cmdInterface, CommandInfo info) {
        SecurityLevel securityLevel = cmdInterface.securityLevel();
        if (securityLevel.isAuthorized(info.getMember())) {
            cmdInterface.run(info);
        } else {
            info.getChannel().sendMessage(MsgBuilder.missingAuthorization()).queue();
        }
    }
}
