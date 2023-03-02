package tech.goksi.tabbycontrol.api.exceptions;

public class WsValidationException extends WebSocketException {
    public WsValidationException(String message) {
        super("invalid_input", message);
    }

    public WsValidationException(String rawMessage, Object... replacements) {
        super("invalid_input", rawMessage, replacements);
    }
}
