package commands;

import core.NotifyConsole;
import database.DATA;
import messages.MsgBuilder;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.util.Arrays;
import java.util.stream.Collectors;

public class CommandInfo {

    //prefix . invoke/agrument0 . argumen1 . argument2 ...

    private String raw;
    private String prefix;
    private String[] arguments;
    private boolean isCommand;

    private Message message;
    private User user;
    private Member member;
    private Guild guild;
    private JDA jda;
    private TextChannel channel;

    public CommandInfo(GuildMessageReceivedEvent event) {
        this.raw = event.getMessage().getContentRaw();
        this.prefix = DATA.guild(event.getGuild()).getPrefix();
        //Only parse when raw fits the command pattern (Starts with the invoke and has more content than just the invoke)
        if (raw.startsWith(prefix) && raw.length() > prefix.length()) {
            this.isCommand = true;
            this.raw = this.raw.replaceFirst(prefix, "");
            parseInfo(event);
        } else {
            this.isCommand = false;
        }
    }

    private void parseInfo(GuildMessageReceivedEvent event) {
        this.arguments = raw.split(" ");
        this.message = event.getMessage();
        this.user = event.getAuthor();
        this.member = event.getMember();
        this.guild = event.getGuild();
        this.jda = event.getJDA();
        this.channel = event.getChannel();
    }

    public boolean isCommand() {
        return this.isCommand;
    }

    public String getInvoke() {
        return getArgument(0).toLowerCase();
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

    public String getPrefix() {
        return this.prefix;
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

    public Message getMessage() {
        return message;
    }

    public User getUser() {
        return this.user;
    }

    public Member getMember() {
        return this.member;
    }

    public Guild getGuild() {
        return this.guild;
    }

    public JDA getJDA() {
        return this.jda;
    }

    public TextChannel getChannel() {
        return this.channel;
    }

    public void wrongSyntax() {
        MessageEmbed embed = MsgBuilder.wrongSyntaxInfo(this.prefix, this.arguments[0]);
        this.channel.sendMessage(embed).queue();
    }

    private boolean isValidIndex(int index) {
        return this.arguments.length > index;
    }
}
