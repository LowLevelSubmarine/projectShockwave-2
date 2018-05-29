package commands.statistic_handling;

import commands.music_handling.GuildPlayer;
import core.JDAHandler;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.PrivateChannel;
import net.dv8tion.jda.core.entities.User;
import tools.Table;

import java.util.ArrayList;

public class UserDetails implements StatInterface {

    @Override
    public String title() {
        return "Details of users " + JDAHandler.getUsername() + " is connected to in some way";
    }

    @Override
    public String render() {
        ArrayList<User> users = getAllUsers();
        Table table = new Table();
        table.setTitles("Username", "Id", "Avatar");
        table.setSizes(40, 18, 90);
        table.setAlignments(Table.ALIGN.LEFT, Table.ALIGN.CENTER, Table.ALIGN.LEFT);
        for (User user : users) {
            table.addRow(user.getName() + "#" + user.getDiscriminator(), user.getId(), user.getEffectiveAvatarUrl());
        }
        String text = "Usercount: " + users.size();
        return text + "\n" + table;
    }

    private ArrayList<User> getAllUsers() {
        ArrayList<User> allUsers = new ArrayList<>();
        for (Guild guild : JDAHandler.getJDA().getGuilds()) {
            for (Member member : guild.getMembers()) {
                if (!allUsers.contains(member.getUser())) {
                    allUsers.add(member.getUser());
                }
            }
        }
        for (PrivateChannel channel : JDAHandler.getJDA().getPrivateChannels()) {
            if (allUsers.contains(channel.getUser())) {
                allUsers.add(channel.getUser());
            }
        }
        return allUsers;
    }
}
