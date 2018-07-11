package commands.administration;

import commands.handling.*;
import core.BotHandler;
import messages.MsgBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;

public class Shutdown implements CommandInterface, ButtonHook {

    private static final String buttonAccept = "✅";
    private static final String buttonDeny = "❌";
    private String shutdownReason;
    private User user;

    @Override
    public String invoke() {
        return "shutdown";
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
        TextChannel textChannel = info.getChannel();
        this.shutdownReason = info.getRaw(1);
        this.user = info.getUser();
        Message message = textChannel.sendMessage(MsgBuilder.shutdownQuerry(this.shutdownReason)).complete();
        message.addReaction(buttonAccept).queue();
        message.addReaction(buttonDeny).queue();
        ButtonHandler.registerTicket(message, this);
    }

    @Override
    public void onButtonPress(ButtonEvent event) {
        if (this.user.equals(event.getUser())) {
            switch (event.getEmote()) {
                case buttonAccept:
                    event.getMessageLink().getMessage().delete().complete();
                    BotHandler.shutdown(this.shutdownReason);
                    break;
                case buttonDeny:
                    event.getMessageLink().getMessage().delete().queue();
                    break;
                default:
                    event.reregister();
                    break;
            }
        } else {
            event.reregister();
        }
    }

    @Override
    public String title() {
        return "Fährt " + BotHandler.getUsername() + " herunter";
    }

    @Override
    public String description() {
        return "Fährt " + BotHandler.getUsername() + " herunter und zeigt gegebenenfalls einen Grund dafür an. Der Vorgang muss zunächst bestätigt werden.";
    }

    @Override
    public String syntax(String p) {
        return p + "shutdown < _ | Grund >";
    }
}
