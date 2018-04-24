package core;

public class Main {

    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new ShutdownHook());
        JDAHandler.boot();
    }
}
