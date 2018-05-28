package commands.handling;

import net.dv8tion.jda.core.entities.Message;

import java.util.HashMap;
import java.util.Map;

public class ButtonHandler {
    private static Map<MessageLink, ButtonHook> TICKETS = new HashMap<>();

    public static void fire(ButtonEvent event) {
        if (TICKETS.containsKey(event.getMessageLink())) {
            ButtonHook hook = TICKETS.get(event.getMessageLink());
            hook.onButtonPress(event);
        }
    }

    public static void registerTicket(Message message, ButtonHook hook) {
        TICKETS.put(new MessageLink(message), hook);
    }
    public static void registerTicket(MessageLink link, ButtonHook hook) {
        TICKETS.put(link, hook);
    }
    public static void registerTicket(ButtonEvent event, ButtonHook hook) {
        TICKETS.put(event.getMessageLink(), hook);
    }
}
