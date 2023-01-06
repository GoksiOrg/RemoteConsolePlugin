package tech.goksi.remoteconsole.api.models.events;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.logging.log4j.core.LogEvent;

import java.util.Arrays;

public class ConsoleOutputEvent extends GenericEvent {
    private final long timestamp;
    private final String message;
    private final String logLevel;
    private final String loggerName;
    private final String stackTrace;

    public ConsoleOutputEvent(LogEvent logEvent) {
        super("console_output", Arrays.asList(
                logEvent.getTimeMillis(),
                logEvent.getLevel().toString(),
                logEvent.getLoggerName(),
                logEvent.getMessage().getFormattedMessage(),
                logEvent.getThrown() == null ? null : ExceptionUtils.getStackTrace(logEvent.getThrown())
        ));
        this.timestamp = (long) getData().get(0);
        this.logLevel = (String) getData().get(1);
        this.loggerName = (String) getData().get(2);
        this.message = (String) getData().get(3);
        this.stackTrace = (String) getData().get(4);
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    public String getLogLevel() {
        return logLevel;
    }

    public String getLoggerName() {
        return loggerName;
    }

    public String getStackTrace() {
        return stackTrace;
    }
}
