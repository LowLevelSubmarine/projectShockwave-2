package commands;

import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.guild.react.GenericGuildMessageReactionEvent;

public class MessageLink {
    private TextChannel channel;
    private String messageId;

    public MessageLink(Message message) {
        this.channel = message.getTextChannel();
        this.messageId = message.getId();
    }

    public MessageLink(GenericGuildMessageReactionEvent event) {
        this.channel = event.getChannel();
        this.messageId = event.getMessageId();
    }

    public MessageLink(TextChannel channel, String messageId) {
        this.channel = channel;
        this.messageId = messageId;
    }

    public Message getMessage() {
        return this.channel.getMessageById(messageId).complete();
    }
}
