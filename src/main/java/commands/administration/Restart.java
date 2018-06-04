package commands.administration;

import commands.handling.*;
import core.JDAHandler;
import messages.MsgBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;

public class Restart implements CommandInterface, ButtonHook {

    private static final String buttonAccept = "✅";
    private static final String buttonDeny = "❌";
    private String restartReason;
    private User user;

    @Override
    public String invoke() {
        return "restart";
    }

    @Override
    public boolean silent() {
        return false;
    }

    @Override
    public CommandType type() {
        return CommandType.ADMINISTRATION;
    }

    @Override
    public SecurityLevel securityLevel() {
        return SecurityLevel.BOT;
    }

    @Override
    public void run(CommandInfo info) {
        TextChannel channel = info.getChannel();
        this.restartReason = info.getRaw(1);
        this.user = info.getUser();
        Message message = channel.sendMessage(MsgBuilder.restartQuerry(this.restartReason)).complete();
        message.addReaction(buttonAccept).queue();
        message.addReaction(buttonDeny).queue();
        ButtonHandler.registerTicket(message, this);
    }

    @Override
    public void onButtonPress(ButtonEvent event) {
        if (event.getUser().equals(this.user)) {
            switch (event.getEmote()) {
                case buttonAccept:
                    event.getMessageLink().getMessage().delete().complete();
                    JDAHandler.restart(this.restartReason);
                    break;
                case buttonDeny:
                    ButtonHandler.registerTicket(event, this);
                    break;
                default:
                    ButtonHandler.registerTicket(event, this);
                    break;
            }
        }
    }

    @Override
    public String title() {
        return "Startet " + JDAHandler.getUsername() + " neu";
    }

    @Override
    public String description() {
        return "Startet " + JDAHandler.getUsername() + " neu und gibt gegebenenfalls einen Grund dafür an. Der Vorgang muss zunächst bestätigt werden";
    }

    @Override
    public String syntax(String p) {
        return p + "restart < _ | Grund >";
    }
}
