package commands.handling;

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
        return this.channel.getMessageById(this.messageId).complete();
    }

    @Override
    public String toString() {
        return "MSGLNK:" + this.channel.getId() + "-" + this.messageId;
    }

    @Override
    public int hashCode() {
        return (this.channel.getId() + this.messageId).hashCode();
    }

    @Override
    public boolean equals(Object object) {
        try {
            MessageLink messageLink = (MessageLink) object;
            return messageLink.channel.equals(this.channel) && messageLink.messageId.equals(this.messageId);
        } catch (ClassCastException e) {
            return false;
        }
    }
}
