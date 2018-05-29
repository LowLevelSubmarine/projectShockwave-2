package core;

import data.DATA;
import fr.bmartel.protocol.http.utils.ExceptionUtils;
import messages.MsgBuilder;
import net.dv8tion.jda.core.entities.PrivateChannel;
import net.dv8tion.jda.core.entities.User;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ExceptionLogger {

    private final static String EXCEPTIONLOGFILENAME = "errors.txt";
    private static BufferedWriter writer;

    public static void log(Exception exception) {
        String message = ExceptionUtils.getExceptionMessage(exception);
        writeToLogFile(message);
        contactDebugSupporter(message);
    }

    public static void log(Throwable throwable) {
        String message = throwable.getMessage() + " / " + throwable.getLocalizedMessage();
        writeToLogFile(message);
        contactDebugSupporter(message);
    }



    private static void checkWriter() {
        if (writer == null) {
            try {
                File logFile = new File(EXCEPTIONLOGFILENAME);
                if (!logFile.exists()) {
                    logFile.createNewFile();
                }
                writer = new BufferedWriter(new FileWriter(logFile, true));
            } catch (IOException e) {
                NotifyConsole.debug(ExceptionLogger.class, "An error while trying create or get exceptionlogfile");
            }
        }
    }
    private static void writeToLogFile(String message) {
        try {
            checkWriter();
            writer.write(message);
            writer.newLine();
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            NotifyConsole.debug(ExceptionLogger.class, "An error occurred while writen to exceptionlogfile");
        }
    }
    private static void contactDebugSupporter(String message) {
        try {
            for (String debugSupporterId : DATA.config().getDebugSupporters()) {
                User user = JDAHandler.getJDA().getUserById(debugSupporterId);
                PrivateChannel channel = user.openPrivateChannel().complete();
                channel.sendMessage(MsgBuilder.exceptionLogInfo(message)).queue();
            }
        } catch (Exception e) {
            NotifyConsole.debug(ExceptionLogger.class, "An error occurred while trying to contact debugSupporter");
        }
    }
}
