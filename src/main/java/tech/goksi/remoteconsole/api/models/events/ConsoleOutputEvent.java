package tech.goksi.remoteconsole.api.models.events;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.LogEvent;

import java.util.Arrays;

/*TODO check exception, thrown*/
public class ConsoleOutputEvent extends GenericEvent {
    private final long timestamp;
    private final String message;
    private final Level logLevel;
    private final String loggerName;
    /*TODO level empty, check*/
    public ConsoleOutputEvent(LogEvent logEvent) {
        super("console_output", Arrays.asList(logEvent.getTimeMillis(), logEvent.getLevel(), logEvent.getLoggerName(), logEvent.getMessage().getFormattedMessage()));
        this.timestamp = (long) getData().get(0);
        this.logLevel = (Level) getData().get(1);
        this.loggerName = (String) getData().get(2);
        this.message = (String) getData().get(3);
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    public Level getLogLevel() {
        return logLevel;
    }

    public String getLoggerName() {
        return loggerName;
    }
}
