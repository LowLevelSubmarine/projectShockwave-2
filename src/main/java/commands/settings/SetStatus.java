package commands.settings;

import commands.CommandInfo;
import commands.CommandInterface;
import commands.SecurityLevel;
import core.JDAHandler;
import core.Toolkit;
import database.DATA;
import messages.MsgBuilder;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.entities.MessageEmbed;

public class SetStatus implements CommandInterface {
    @Override
    public SecurityLevel securityLevel() {
        return SecurityLevel.BOT;
    }

    @Override
    public void run(CommandInfo info) {
        String gameTypeRaw = info.getArgument(1);
        String text = info.getRaw(2);
        if (text == null || !Toolkit.isOneOf(gameTypeRaw.toLowerCase(), "playing", "streaming", "watching", "listening")) {
            info.wrongSyntax();
            return;
        }
        Game.GameType gameType = Game.GameType.valueOf(gameTypeRaw.toUpperCase());
        Game game = Game.of(gameType, text);
        DATA.bot().setGame(game);
        JDAHandler.getJDA().getPresence().setGame(game);
        MessageEmbed embed = MsgBuilder.setStatusDone();
        info.getChannel().sendMessage(embed).queue();
    }

    @Override
    public String category() {
        return "Administration";
    }

    @Override
    public String title() {
        return "Ändert den Status";
    }

    @Override
    public String description() {
        return "Stellt ein was der Bot als \"spielt/streamt/guckt/hört ...\" anzeigt" ;
    }

    @Override
    public String syntax(String p) {
        return p +"setstatus < playing | streaming | watching | listening > < text >";
    }
}
