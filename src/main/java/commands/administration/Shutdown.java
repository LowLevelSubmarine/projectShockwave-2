package commands.administration;

import commands.CommandInfo;
import commands.CommandInterface;
import commands.SecurityLevel;

public class Shutdown implements CommandInterface {
    @Override
    public SecurityLevel securityLevel() {
        return SecurityLevel.BOT;
    }

    @Override
    public void run(CommandInfo info) {

    }

    @Override
    public String title() {
        return "Fährt den Bot herunter";
    }

    @Override
    public String description() {
        return "Fährt den Bot herunter und zeigt gegebenenfalls einen Grund dafür. Der Vorgang muss zuerst bestätigt werden.";
    }

    @Override
    public String syntax(String p) {
        return p + "shutdown < _ | GRUND >";
    }
}
