package commands.handling;

public interface CommandInterface {
    String invoke();
    CommandType type();
    SecurityLevel securityLevel();
    void run(CommandInfo info);
    String title();
    String description();
    String syntax(String p);
}
