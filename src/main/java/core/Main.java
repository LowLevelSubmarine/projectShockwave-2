package core;

import commands.CommandHandler;
import commands.administration.Shutdown;

public class Main {

    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new ShutdownHook());
        JDAHandler.boot();
    }

    public static void addCommands() {
        CommandHandler.addCommand("shutdown", Shutdown.class);
    }
}
