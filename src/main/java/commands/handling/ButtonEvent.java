package commands.handling;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.guild.react.GenericGuildMessageReactionEvent;

public class ButtonEvent {
    private MessageLink messageLink;
    private ButtonHook hook;
    private String emote;
    private User user;
    private Member member;
    private Guild guild;

    public ButtonEvent(GenericGuildMessageReactionEvent event) {
        this.messageLink = new MessageLink(event);
        this.emote = event.getReactionEmote().getName();
        this.user = event.getUser();
        this.member = event.getMember();
        this.guild= event.getGuild();
    }

    public void reregister() {
        ButtonHandler.registerTicket(this);
    }

    public MessageLink getMessageLink() {
        return this.messageLink;
    }
    public void setHook(ButtonHook hook) {
        this.hook = hook;
    }
    public ButtonHook getHook() {
        return this.hook;
    }
    public String getEmote() {
        return this.emote;
    }
    public boolean is(String emote) {
        return this.emote.equals(emote);
    }
    public User getUser() {
        return this.user;
    }
    public Member getMember() {
        return member;
    }
    public Guild getGuild() {
        return guild;
    }
}
