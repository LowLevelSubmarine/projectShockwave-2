package commands;

import messages.MsgBuilder;

import java.util.*;

public class CommandHandler {
    private static HashMap<String, Class> COMMANDS = new HashMap<>();
    private static String COMMANDLIST = null;

    public static void fire(CommandInfo info) {
        CommandInterface cmInterface = getCommandInterface(info.getInvoke());
        if (cmInterface != null) {
            checkPermissionAndRun(cmInterface, info);
        }
    }

    public static void addCommand(String invoke, Class command) {
        COMMANDS.put(invoke, command);
    }

    public static CommandInterface getCommandInterface(String invoke) {
        invoke = invoke.toLowerCase();
        if (COMMANDS.containsKey(invoke)) {
            try {
                return (CommandInterface) COMMANDS.get(invoke).newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                return null;
            }
        } else {
            return null;
        }
    }

    public static String getCommandList() {
        return COMMANDLIST;
    }

    public static void renderCommandList() {
        COMMANDLIST = "";
        List<String> invokes = new LinkedList<>();
        invokes.addAll(COMMANDS.keySet());
        Collections.sort(invokes);
        for (String invoke : invokes) {
            CommandInterface cmdInterface = getCommandInterface(invoke);
            COMMANDLIST += "\n-" + invoke + " [" + cmdInterface.title() + "]";
        }
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
