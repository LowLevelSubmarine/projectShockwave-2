package commands;

import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Member;

import java.util.ArrayList;

public enum SecurityLevel {
    NONE, GUILD, BOT, OWNER;

    public boolean isAuthorized(Member member) {
        switch (this) {
            case NONE:
                return true;
            case GUILD:
                return member.hasPermission(Permission.ADMINISTRATOR);
            case BOT:
                return true; //TODO
            case OWNER:
                return true; //TODO
        }
        return false;
    }

    public ArrayList<String> getOtherPermissionOfO
}
