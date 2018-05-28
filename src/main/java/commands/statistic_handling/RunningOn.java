package commands.statistic_handling;

import core.JDAHandler;
import net.dv8tion.jda.core.entities.Guild;
import tools.Table;

public class RunningOn implements StatisticInterface {
    @Override
    public String title() {
        return JDAHandler.getUsername() + " is running on following Guilds:";
    }

    @Override
    public String render() {
        Table table = new Table();
        table.setTitles("Name", "Id", "Membercount", "Owner");
        table.setSizes(50, 18, 11, 50);
        table.setAlignments(Table.ALIGN.LEFT, Table.ALIGN.CENTER, Table.ALIGN.RIGHT, Table.ALIGN.LEFT);
        for(Guild guild : JDAHandler.getJDA().getGuilds()) {
            table.addRow(guild.getName(), guild.getId(), guild.getMembers().size() + "", guild.getOwner().getUser().getName() + "#" + guild.getOwner().getUser().getDiscriminator());
        }
        return "\n" + table;
    }
}
