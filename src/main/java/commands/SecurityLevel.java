package commands;

import database.DATA;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.User;

import java.util.ArrayList;
import java.util.List;

public enum SecurityLevel {

    NONE, GUILD, BOT, OWNER;

    private static final String NONE_EMOJIE = "❌";
    private static final String GUILD_EMOJIE = "⚪";
    private static final String BOT_EMOJIE = "\uD83D\uDD35";
    private static final String OWNER_EMOJIE = "\uD83D\uDD34";

    public boolean isAuthorized(Member member) {
        switch (this) {
            case NONE:
                return true;
            case GUILD:
                return isGuildAdmin(member);
            case BOT:
                return isBotOwner(member.getUser()) || isBotAdmin(member.getUser());
            case OWNER:
                return isBotOwner(member.getUser());
        }
        return false;
    }

    public String getTitle() {
        switch (this) {
            case NONE:
                return "Keine";
            case GUILD:
                return "Guild";
            case BOT:
                return "Bot";
            case OWNER:
                return "Owner";
        }
        return "";
    }

    public String getEmojie() {
        switch (this) {
            case NONE:
                return NONE_EMOJIE;
            case GUILD:
                return GUILD_EMOJIE;
            case BOT:
                return BOT_EMOJIE;
            case OWNER:
                return OWNER_EMOJIE;
        }
        return "";
    }

    @Override
    public String toString() {
        return this.getEmojie() + " - " + this.getTitle();
    }

    private boolean isGuildAdmin(Member member) {
        return member.hasPermission(Permission.ADMINISTRATOR);
    }
    private boolean isBotAdmin(User user) {
        ArrayList<String> botAdmins = DATA.bot().getBotAdmins();
        return botAdmins.contains(user.getId());
    }
    private boolean isBotOwner(User user) {
        List<String> botOwners = DATA.config().getOwners();
        return botOwners.contains(user.getId());
    }
}
