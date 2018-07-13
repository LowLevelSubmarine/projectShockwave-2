package core;

import messages.MsgBuilder;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.PrivateChannel;

public class PermissionChecker {
    public static void checkPermission(Guild guild) {
        if (!guild.getSelfMember().hasPermission(Permission.ADMINISTRATOR)) {
            MessageEmbed embed = MsgBuilder.noPermissions();
            PrivateChannel channel = guild.getOwner().getUser().openPrivateChannel().complete();
            channel.sendMessage(embed).complete();
            guild.leave().queue();
        }
    }
    public static void checkPermissions(JDA jda) {
        for (Guild guild : jda.getGuilds()) {
            checkPermission(guild);
        }
    }
}
