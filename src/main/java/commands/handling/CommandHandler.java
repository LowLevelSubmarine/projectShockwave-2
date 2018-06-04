package commands.handling;

import core.ExceptionLogger;
import messages.MsgBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;

import java.util.*;

public class CommandHandler {
    private static HashMap<String, CommandInterface> COMMANDS = new HashMap<>();
    private static HashMap<String, String> COMMANDLIST = null;

    public static void fire(CommandInfo info) {
        String invoke = info.getInvoke();
        //Check if command exists
        if (commandExists(invoke)) {
            CommandInterface command = getCommand(invoke);
            SecurityLevel securityLevel = command.securityLevel();
            if (securityLevel.isAuthorized(info.getMember())) {
                if (command.silent()) info.getMessage().delete().queue();
                command.run(info);
            } else {
                MessageEmbed embed = MsgBuilder.missingAuthorization();
                info.getChannel().sendMessage(embed).queue();
            }
        }
    }

    public static void addCommand(CommandInterface cmdInterface) {
        COMMANDS.put(cmdInterface.invoke(), cmdInterface);
    }

    public static boolean commandExists(String invoke) {
        return COMMANDS.containsKey(invoke);
    }

    public static CommandInterface getCommand(String invoke) {
        invoke = invoke.toLowerCase();
        if (commandExists(invoke)) {
            try {
                return COMMANDS.get(invoke).getClass().newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                ExceptionLogger.log(e);
            }
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
            commands.add("*" + cmdInterface.invoke().toLowerCase() + "* - " + cmdInterface.title());
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
}
