package commands.settings;

import commands.CommandInfo;
import commands.CommandInterface;
import commands.SecurityLevel;

public class SetServerSnappys implements CommandInterface {
    @Override
    public SecurityLevel securityLevel() {
        return SecurityLevel.GUILD;
    }

    @Override
    public void run(CommandInfo info) {

    }

    @Override
    public String category() {
        return "Einstellungen";
    }

    @Override
    public String title() {
        return "Tracks kürzen";
    }

    @Override
    public String description() {
        return "Kürzt die Eingabe für den play Command auf eine angegebene Zeichenfolge";
    }

    @Override
    public String syntax(String p) {
        return p + "setserversnappys < Schlüssel > < _ | Schlagwort oder Link >";
    }
}
