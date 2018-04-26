package commands;

import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.util.Arrays;
import java.util.stream.Collectors;

public class CommandInfo {

    //prefix . invoke/agrument0 . argumen1 . argument2 ...

    private String raw;
    private String[] arguments;

    private User user;
    private Member member;
    private Guild guild;
    private TextChannel channel;

    public CommandInfo(GuildMessageReceivedEvent event) {
        this.raw = event.getMessage().getContentRaw();
        this.arguments = raw.split(" ");
        this.user = event.getAuthor();
        this.member = event.getMember();
        this.guild = event.getGuild();
        this.channel = event.getChannel();
    }

    public String getInvoke() {
        return getArgument(0);
    }
    public String getRaw() {
        return this.raw;
    }
    public String getRaw(int index) {
        if (isValidIndex(index)) {
            return Arrays.stream(this.arguments).skip(index).map(s -> " " + s).collect(Collectors.joining()).substring(1);
        } else {
            return null;
        }
    }
    public String[] getArguments() {
        return this.arguments;
    }
    public String getArgument(int index) {
        if (isValidIndex(index)) {
            return this.arguments[index];
        } else {
            return null;
        }
    }

    private boolean isValidIndex(int index) {
        return this.arguments.length > index;
    }
}
