package core;

import messages.MsgBuilder;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.PrivateChannel;

public class PermissionChecker implements Runnable {

    private static final int CHECKAFTER = 1000;

    private Guild guild;

    public PermissionChecker(Guild guild) {
        this.guild = guild;
    }
    @Override
    public void run() {
        try {
            Thread.sleep(CHECKAFTER);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (!this.guild.getSelfMember().hasPermission(Permission.ADMINISTRATOR)) {
            MessageEmbed embed = MsgBuilder.noPermissions(this.guild.getOwner());
            PrivateChannel channel = this.guild.getOwner().getUser().openPrivateChannel().complete();
            channel.sendMessage(embed).complete();
            this.guild.leave().queue();
        }
    }

    public static void checkPermission(Guild guild) {
        new Thread(new PermissionChecker(guild)).start();
    }
    public static void checkPermissions(JDA jda) {
        for (Guild guild : jda.getGuilds()) {
            checkPermission(guild);
        }
    }
}
