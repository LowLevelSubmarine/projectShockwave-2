package listeners;

import statics.ExceptionLogger;
import net.dv8tion.jda.core.events.ExceptionEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class ExceptionListener extends ListenerAdapter {
    @Override
    public void onException(ExceptionEvent event) {
        ExceptionLogger.log(event.getCause());
    }
}
