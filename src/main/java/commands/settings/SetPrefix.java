package commands.settings;

import commands.CommandHandler;
import commands.CommandInfo;
import commands.CommandInterface;
import commands.SecurityLevel;
import core.Statics;
import core.Toolkit;
import database.DATA;
import messages.MsgBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;

public class SetPrefix implements CommandInterface {

    @Override
    public SecurityLevel securityLevel() {
        return SecurityLevel.GUILD;
    }

    @Override
    public void run(CommandInfo info) {
        //Check if new prefix is given and get it
        String newPrefix = info.getArgument(1);
        if (newPrefix == null) {
            info.wrongSyntax();
            return;
        }

        //Get old prefix
        String oldPrefix = info.getPrefix();

        //Shorten new prefix if necessary
        newPrefix = Toolkit.trimString(newPrefix, Statics.MAXPREFIXLENGTH);

        //Update database
        DATA.guild(info.getGuild()).setPrefix(newPrefix);

        //Send message
        MessageEmbed embed = MsgBuilder.setPrefixDone(oldPrefix, newPrefix);
        info.getChannel().sendMessage(embed).queue();
    }

    @Override
    public String category() {
        return "Infos";
    }

    @Override
    public String title() {
        return "Stellt das prefix um";
    }

    @Override
    public String description() {
        return "Legt fest welches Prefix auf dem ausführenden Server genutzt werden soll um Commands zu markieren. " +
                "Das angegebene Prefix wird für die Benutzerfreundlichkeit, " +
                "falls länger auf eine Länge von " + Statics.MAXPREFIXLENGTH +" Zeichen gekürzt.";
    }

    @Override
    public String syntax(String p) {
        return p + "setprefix < Prefix >";
    }
}
