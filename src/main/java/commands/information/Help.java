package commands.information;

import commands.handling.*;
import data.DATA;
import messages.MsgBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.PrivateChannel;

public class Help implements CommandInterface {
    @Override
    public String invoke() {
        return "help";
    }

    @Override
    public CommandType type() {
        return CommandType.INFORMATION;
    }

    @Override
    public SecurityLevel securityLevel() {
        return SecurityLevel.NONE;
    }

    @Override
    public void run(CommandInfo info) {
        String helpcmdInvoke = info.getArgument(1);
        //Check wether the the list of commands or the description of one command should be send
        if (helpcmdInvoke == null) {
            PrivateChannel privateChannel = info.getUser().openPrivateChannel().complete();
            MessageEmbed embed = MsgBuilder.commandList();
            privateChannel.sendMessage(embed).queue();
        } else {
            CommandInterface helpcmdInterface = CommandHandler.getCommandInterface(helpcmdInvoke);
            //Check if the given invoke actually has a corrisponding command
            if (helpcmdInterface == null) {
                MessageEmbed embed = MsgBuilder.commandDescriptionMissingCommand();
                info.getChannel().sendMessage(embed).queue();
            } else {
                String description = helpcmdInterface.description();
                String syntax = helpcmdInterface.syntax(DATA.guild(info.getGuild()).getPrefix());
                MessageEmbed embed = MsgBuilder.commandDescription(helpcmdInvoke, description, syntax);
                info.getChannel().sendMessage(embed).queue();
            }
        }
    }

    @Override
    public String title() {
        return "Eine Liste aller Commands";
    }

    @Override
    public String description() {
        return "Schickt dem Absender die Beschreibung und den Syntax eines bestimmten Commands oder im privaten Chat eine Liste aller Commands";
    }

    @Override
    public String syntax(String p) {
        return p + "help < _ | Command >";
    }
}
