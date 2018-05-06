package commands;

public interface CommandInterface {
    SecurityLevel securityLevel();
    void run(CommandInfo info);
    String category();
    String title();
    String description();
    String syntax(String p);
}
