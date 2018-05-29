package commands.settings;

import commands.handling.CommandInfo;
import commands.handling.CommandInterface;
import commands.handling.CommandType;
import commands.handling.SecurityLevel;
import core.JDAHandler;
import tools.Toolkit;
import data.DATA;
import messages.MsgBuilder;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.entities.MessageEmbed;

public class SetStatus implements CommandInterface {
    @Override
    public String invoke() {
        return "setstatus";
    }

    @Override
    public CommandType type() {
        return CommandType.SETTINGS;
    }

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
    public String title() {
        return "Ändert den Status";
    }

    @Override
    public String description() {
        return "Stellt ein was " + JDAHandler.getUsername() + " als \"spielt/streamt/schaut/hört auf ...\" anzeigt" ;
    }

    @Override
    public String syntax(String p) {
        return p +"setstatus < playing | streaming | watching | listening > < Text >";
    }
}
