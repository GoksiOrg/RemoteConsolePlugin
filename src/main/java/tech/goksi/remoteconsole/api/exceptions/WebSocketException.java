package tech.goksi.remoteconsole.api.exceptions;

import com.google.gson.annotations.Expose;

public class WebSocketException extends RuntimeException {
    @Expose
    private final String error;
    @Expose
    private final String message;

    public WebSocketException(String error, String message) {
        this.message = message;
        this.error = error;
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
