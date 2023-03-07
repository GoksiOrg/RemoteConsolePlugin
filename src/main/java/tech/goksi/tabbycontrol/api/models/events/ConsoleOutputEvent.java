package tech.goksi.tabbycontrol.api.models.events;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.logging.log4j.core.LogEvent;

import static tech.goksi.tabbycontrol.utility.CommonUtility.mapOf;

public class ConsoleOutputEvent extends GenericEvent {

    public ConsoleOutputEvent(LogEvent logEvent) {
        super("console_output", mapOf(
                "timestamp", logEvent.getTimeMillis(),
                "log_level", logEvent.getLevel().toString(),
                "logger_name", logEvent.getLoggerName(),
                "message", logEvent.getMessage().getFormattedMessage(),
                "stacktrace", logEvent.getThrown() == null ? null : ExceptionUtils.getStackTrace(logEvent.getThrown())
        ));
    }
}
