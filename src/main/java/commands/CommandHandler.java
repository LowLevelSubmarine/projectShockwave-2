package commands;

import messages.MsgBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CommandHandler {
    private static HashMap<String, Class> COMMANDS = new HashMap<>();
    private static HashMap<String, String> COMMANDLIST = null;

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

    public static Map<String, String> getCommandList() {
        return COMMANDLIST;
    }

    public static void renderCommandList() {
        //Get all commands
        HashMap<String, ArrayList<String>> commandList = new HashMap<>();
        for (String invoke : COMMANDS.keySet()) {
            CommandInterface cmdInterface = getCommandInterface(invoke);
            ArrayList<String> categoryList = commandList.getOrDefault(cmdInterface.category(), new ArrayList<>());
            categoryList.add(invoke + " - " + cmdInterface.title());
            commandList.put(cmdInterface.category(), categoryList);
        }
        //Sort commands inside of the categorys and add them as a String to the global commandlist maü
        COMMANDLIST = new HashMap<>();
        for (String categoryName : commandList.keySet()) {
            ArrayList<String> categoryList = commandList.get(categoryName);
            Collections.sort(categoryList);
            String categoryListString = "";
            for (String singeLine : categoryList) {
                categoryListString += "\n" + singeLine;
            }
            COMMANDLIST.put(categoryName, categoryListString);
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
