package commands.administration;

import commands.*;
import messages.MsgBuilder;
import net.dv8tion.jda.core.entities.TextChannel;

public class Shutdown implements CommandInterface, ButtonHook {
    @Override
    public SecurityLevel securityLevel() {
        return SecurityLevel.BOT;
    }

    @Override
    public void run(CommandInfo info) {
        TextChannel textChannel = info.getChannel();
        String reason = info.getRaw(1);
        textChannel.sendMessage(MsgBuilder.shutdownQuerry(reason)).queue(m -> ButtonHandler.registerTicket(m, this));
    }

    @Override
    public void onButtonPress(ButtonEvent event) {

    }

    @Override
    public String title() {
        return "Fährt den Bot herunter";
    }

    @Override
    public String description() {
        return "Fährt den Bot herunter und zeigt gegebenenfalls einen Grund dafür. Der Vorgang muss zuerst bestätigt werden.";
    }

    @Override
    public String syntax(String p) {
        return p + "shutdown < _ | GRUND >";
    }
}
