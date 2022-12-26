package tech.goksi.remoteconsole.api.exceptions;

public class WebSocketException {
    private final String error;
    private final String message;

    public WebSocketException(String error, String message) {
        this.error = error;
        this.message = message;
    }

    public WebSocketException(String error, String rawMessage, Object... replacements) {
        this(error, String.format(rawMessage, replacements));
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }
}
