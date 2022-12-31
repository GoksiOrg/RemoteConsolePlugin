package tech.goksi.remoteconsole.api.exceptions;

import com.google.gson.annotations.Expose;

/*TODO probably make it throw*/
public class WebSocketException {
    @Expose
    private final String error;
    @Expose
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
