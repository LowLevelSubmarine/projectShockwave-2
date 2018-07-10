package commands.handling;

import net.dv8tion.jda.core.entities.Message;

import java.util.HashMap;
import java.util.Map;

public class ButtonHandler {
    private static Map<MessageLink, ButtonHook> TICKETS = new HashMap<>();

    public static void fire(ButtonEvent event) {
        if (TICKETS.containsKey(event.getMessageLink())) {
            ButtonHook hook = TICKETS.get(event.getMessageLink());
            event.setHook(hook);
            hook.onButtonPress(event);
            //TODO: rethink
        }
    }

    public static void registerTicket(Message message, ButtonHook hook) {
        TICKETS.put(new MessageLink(message), hook);
    }

    static void registerTicket(ButtonEvent event) {
        TICKETS.put(event.getMessageLink(), event.getHook());
    }

    public static void revokeTicket(Message message) {
        TICKETS.remove(new MessageLink(message));
    }
}
