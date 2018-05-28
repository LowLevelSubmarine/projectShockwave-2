package commands.handling;

import messages.MsgBuilder;

import java.util.*;

public class CommandHandler {
    private static HashMap<String, CommandInterface> COMMANDS = new HashMap<>();
    private static HashMap<String, String> COMMANDLIST = null;

    public static void fire(CommandInfo info) {
        CommandInterface cmInterface = getCommandInterface(info.getInvoke());
        if (cmInterface != null) {
            checkPermissionAndRun(cmInterface, info);
        }
    }

    public static void addCommand(CommandInterface cmdInterface) {
        COMMANDS.put(cmdInterface.invoke(), cmdInterface);
    }

    public static CommandInterface getCommandInterface(String invoke) {
        invoke = invoke.toLowerCase();
        try {
            return COMMANDS.get(invoke).getClass().newInstance();
        } catch (InstantiationException | IllegalAccessException | NullPointerException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Map<String, String> getCommandList() {
        return COMMANDLIST;
    }

    public static void renderCommandList() {
        //Add
        HashMap<String, ArrayList<String>> commandsByType = new HashMap<>();
        for (CommandInterface cmdInterface : COMMANDS.values()) {
            ArrayList<String> commands = commandsByType.getOrDefault(cmdInterface.type().getName(), new ArrayList<>());
            commands.add("" + cmdInterface.invoke().toLowerCase() + " - " + cmdInterface.title());
            commandsByType.put(cmdInterface.type().getName(), commands);
        }
        //Sort
        LinkedHashMap<String, String> sortedCommandsBySortedTypes = new LinkedHashMap<>();
        ArrayList<String> sortedTypes = new ArrayList(commandsByType.keySet());
        Collections.sort(sortedTypes);
        for (String type : sortedTypes) {
            ArrayList<String> commands = commandsByType.get(type);
            String commandsString = "";
            Collections.sort(commands);
            for (String command : commands) {
                commandsString += "\n" + command;
            }
            sortedCommandsBySortedTypes.put(type, commandsString);
        }

        //EXPORT
        COMMANDLIST = sortedCommandsBySortedTypes;
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
