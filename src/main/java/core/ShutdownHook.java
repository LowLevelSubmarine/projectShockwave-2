package core;

public class ShutdownHook extends Thread {

    private static final String DEFAULTSHUTDOWNREASON = null;

    @Override
    public void run() {
        JDAHandler.shutdown(DEFAULTSHUTDOWNREASON);
    }
}
