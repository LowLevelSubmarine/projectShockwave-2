package commands.statistic_handling;

import core.BotHandler;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import tools.Table;

public class GuildDetails implements StatInterface {
    @Override
    public String title() {
        return "Details about the members of the guilds " + BotHandler.getUsername() + " is running on:";
    }

    @Override
    public String render() {
        String rendered = "";
        for (Guild guild : BotHandler.getJDA().getGuilds()) {
            Table table = new Table();
            table.setTitles("Effective name", "Username", "Id");
            table.setSizes(50, 50, 18);
            table.setAlignments(Table.ALIGN.LEFT, Table.ALIGN.LEFT, Table.ALIGN.CENTER);
            for (Member member : guild.getMembers()) {
                table.addRow(member.getEffectiveName(), member.getUser().getName() + "#" + member.getUser().getDiscriminator(), member.getUser().getId());
            }
            rendered += guild.getName().toUpperCase() + " - " + guild.getId() + "\n" + table;
        }
        return rendered;
    }
}
